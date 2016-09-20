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
    private static final String DATABASE_NAME = "fitnesstracker.db";
    private static final int DATABASE_VERSION = 1;

    //LOGIN INFORMATION TABLE
    private static final String TABLE_LOGININFO = "logininfo";
    //PRIMARY KEY
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_HASHPASS = "hashpass";
    public static final String COLUMN_SALT     = "salt";

    //FOOD AND CALORIES TABLE
    private static final String TABLE_NUTRITION = "nutrition";
    public static final String COLUMN_FOOD = "food";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_LIPIDS = "lipids";
    public static final String COLUMN_CARBOHYDRATES = "carbohydrates";
    public static final String COLUMN_PROTEIN = "protein";
    //TODO: ADD VITAMINS AND SUB-FATS, SUB-CARBS
    //TODO: ADD FITNESS ACTIVITIES TABLE...

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGININFO + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_HASHPASS + " TEXT, " + COLUMN_SALT + " TEXT)";
        String CREATE_TABLE_NUTRITION = "CREATE TABLE " + TABLE_NUTRITION + "(" + COLUMN_FOOD + " TEXT, " + COLUMN_CALORIES + " INTEGER, " + COLUMN_LIPIDS + " INTEGER, " + COLUMN_CARBOHYDRATES + " INTEGER, " + COLUMN_PROTEIN + " INTEGER)";
        db.execSQL(CREATE_TABLE_LOGIN);
        db.execSQL(CREATE_TABLE_NUTRITION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //will do a fall-through switch statement on release if any changes occur in later versions...

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGININFO);
        onCreate(db);
    }

    //Adds user's login information to database
    public void addLoginInfoToDB(LoginInformation loginfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, loginfo.getUsername());
        contentValues.put(COLUMN_HASHPASS, loginfo.getHashPass());
        contentValues.put(COLUMN_SALT,     loginfo.getSalt());
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(TABLE_LOGININFO, null, contentValues);
        database.close();
    }

    //Adds food with its respective calories, lipids, carbs, protein to its table in database
    public void addNutritionInfoToDB(NutritionInformation nutriInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FOOD, nutriInfo.getFood());
        contentValues.put(COLUMN_CALORIES, nutriInfo.getCalories());
        contentValues.put(COLUMN_LIPIDS,     nutriInfo.getLipids());
        contentValues.put(COLUMN_CARBOHYDRATES,     nutriInfo.getCarbs());
        contentValues.put(COLUMN_PROTEIN,     nutriInfo.getProtein());
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(TABLE_NUTRITION, null, contentValues);
        database.close();
    }

    //Retrieves user's login information from database
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

    public NutritionInformation queryNutritionInfoFromDB(String food) {
        String query = "SELECT * FROM " + TABLE_NUTRITION + " WHERE " + COLUMN_FOOD + " =  \"" + food + "\"";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        NutritionInformation nutriInfo = new NutritionInformation();

        //if the result set is not empty
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            nutriInfo.setFood(cursor.getString(0));
            nutriInfo.setCalories(Integer.parseInt(cursor.getString(1)));
            nutriInfo.setLipids(Integer.parseInt(cursor.getString(2)));
            nutriInfo.setCarbs(Integer.parseInt(cursor.getString(3)));
            nutriInfo.setProtein(Integer.parseInt(cursor.getString(4)));
            cursor.close();
        }
        else nutriInfo = null;

        database.close();

        return nutriInfo;
    }

    //deletes row associated with username
    //returns true if successful, false otherwise
    public void deleteLoginInfoFromDB(String username) {
        String[] uname = new String[] {username};
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_LOGININFO, COLUMN_USERNAME + " = ?", uname);
        database.close();
    }

    //deletes row associated with food
    //returns true if successful, false otherwise
    public void deleteNutritionInfoFromDB(String food) {
        String[] key = new String[] {food};
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NUTRITION, COLUMN_FOOD + " = ?", key);
        database.close();
    }
}
