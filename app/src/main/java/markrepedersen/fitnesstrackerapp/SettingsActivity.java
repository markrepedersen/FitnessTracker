package markrepedersen.fitnesstrackerapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by mark on 16-09-11.
 */
public class SettingsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.content, new SettingsFragment());
        ft.commit();
    }
}
