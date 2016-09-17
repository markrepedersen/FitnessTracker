package markrepedersen.fitnesstrackerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import markrepedersen.fitnesstrackerapp.BCrypt;

public class LoginActivity extends AppCompatActivity {
    String admin_username, admin_password;
    Button loginButton, cancelButton;
    EditText unameText, pwText;
    private final int MAX_LOGIN_ATTEMPTS = 5;
    DBHandler dbHandler = new DBHandler(this, null, null, 1);
//fsfsdffdfsf
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        loginButton = (Button) findViewById(R.id.login_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        unameText = (EditText) findViewById(R.id.enter_username);
        pwText = (EditText) findViewById(R.id.enter_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = unameText.getText().toString();
                LoginInformation loginInfo = dbHandler.queryLoginInfoFromDB(username);
                if (loginInfo != null) { // user has registered an account
                    String hashPassAttempt = BCrypt.hashpw(pwText.getText().toString(), loginInfo.getSalt());
                    if (loginInfo.getUsername().equals(unameText.getText().toString())
                            && loginInfo.getHashPass().equals(hashPassAttempt)) {
                        //login info is correct and user can log in successfully
                        //proceeds to next activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        //case where username or password is incorrect
                        //let user try again a MAX_LOGIN_ATTEMPTS amount of times before cutting them off

                    }
                }
                else {  // user has not registered an account yet; can sign up

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("We could not find an account matching those login credentials. Would you like to sign up instead?");
                    builder.setPositiveButton("Yes, please.", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String salt = BCrypt.gensalt();
                            LoginInformation signUpAccInfo = new LoginInformation();
                            signUpAccInfo.setUsername(unameText.getText().toString());
                            signUpAccInfo.setHashPass(BCrypt.hashpw(pwText.getText().toString(), salt));
                            signUpAccInfo.setSalt(salt);
                            dbHandler.addLoginInfoToDB(signUpAccInfo);

                            dialog.cancel();

                        }
                    });

                    builder.setNegativeButton("No, thanks.", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog signUpAlert = builder.create();
                    signUpAlert.show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
