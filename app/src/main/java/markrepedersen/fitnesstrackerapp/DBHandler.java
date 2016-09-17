package markrepedersen.fitnesstrackerapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;

/**
 * Created by mark on 16-08-31.
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "logininfo.db";
    private static final String TABLE_LOGININFO = "logininfo";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_HASHPASS = "hashpass";
    public static final String COLUMN_SALT     = "salt";
    public static final String COLUMN_WEIGHT   = "weight";
    public static final String COLUMN_HEIGHT   = "height";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_LOGININFO + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_HASHPASS + " TEXT, " + COLUMN_SALT + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //will do a fall-through switch statement if any changes occur in later versions...

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGININFO);
        onCreate(db);
    }

    public void addLoginInfoToDB(LoginInformation loginfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, loginfo.getUsername());
        contentValues.put(COLUMN_HASHPASS, loginfo.getHashPass());
        contentValues.put(COLUMN_SALT,     loginfo.getSalt());
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(TABLE_LOGININFO, null, contentValues);
        database.close();
    }

    // ***** MAKE SURE DUPLICATE USERNAMES AREN'T ALLOWED *****
    public LoginInformation queryLoginInfoFromDB(String username) {
        String query = "SELECT * FROM " + TABLE_LOGININFO + " WHERE " + COLUMN_USERNAME + " =  \"" + username + "\"";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        LoginInformation loginInfo = new LoginInformation();

        //if the result set is not empty
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            loginInfo.setId(Integer.parseInt(cursor.getString(0)));
            loginInfo.setUsername(cursor.getString(1));
            loginInfo.setHashPass(cursor.getString(2));
            loginInfo.setSalt(cursor.getString(3));
            cursor.close();
        }
        else loginInfo = null;

        database.close();

        return loginInfo;
    }

    //deletes row associated with username
    //returns true if successful, false otherwise
    public void deleteLoginInfoFromDB(String username) {
        String[] uname = new String[] {username};
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_LOGININFO, COLUMN_USERNAME + " = ?", uname);
        database.close();
    }
}
