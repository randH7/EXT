package com.example.ext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "MyFoodList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "myfood_list";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "food_name";
    private static final String COLUMN_QUANTITY =  "food_quantity";
    private static final String COLUMN_EXPIRY_DATE = "expiry_date";


    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " ( " +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_QUANTITY + " REAL, " +
                        COLUMN_EXPIRY_DATE + " TEXT, user_id INTEGER);  ";
        db.execSQL(query);
        String usersQuery =
                "CREATE TABLE " + "Users" + " ( " +
                        "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "email" + " TEXT, " +
                        "password" + " TEXT, " +
                        "name" + " TEXT); ";
        db.execSQL(usersQuery);
        String settingsQuery =
                "CREATE TABLE " + "Settings" + " ( " +
                        "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "days" + " INTEGER) ";
        ContentValues cv = new ContentValues();
        db.execSQL(settingsQuery);
        cv.put("days",1);

        long result = db.insert("Settings", null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + "Users");
        db.execSQL("DROP TABLE IF EXISTS " + "Settings");
        onCreate(db);
    }

    void addFood(String foodN, double foodQ, String foodED){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, foodN);
        cv.put(COLUMN_QUANTITY, foodQ);
        cv.put(COLUMN_EXPIRY_DATE, foodED);
        cv.put("user_id", MainActivity.user.id);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to Add.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Added!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME + " where user_id = "+ MainActivity.user.id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateOneRow(String row_id, String foodN, String foodQ, String foodED) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, foodN);
        cv.put(COLUMN_QUANTITY, foodQ);
        cv.put(COLUMN_EXPIRY_DATE, foodED);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String [] {row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    int signUp(String email, String password, String name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("email",email);
        cv.put("password", password);
        cv.put("name", name);
        long result = db.insert("Users", null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to sign up.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Sign up complete", Toast.LENGTH_SHORT).show();
        }
        return (int) result;
    }

    User Login(String email,String password) {
        String query = "SELECT * FROM Users where email = '" + email
                + "'and password = '"+password+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        User user ;
        if (cursor.getCount() == 0){
            Toast.makeText(context, "please check your email and password",
                    Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            cursor.moveToNext();
            user = new User (cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3));

        }
        return user;
    }
    int getSettingsDays (){
        String query = "SELECT * FROM Settings" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
            cursor.moveToNext();
            return cursor.getInt(1);
        }
        return 1;
    }
    void updateDays(int days) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("days", days);

        long result = db.update("Settings", cv, "id=?", new String [] {"1"});

    }
}