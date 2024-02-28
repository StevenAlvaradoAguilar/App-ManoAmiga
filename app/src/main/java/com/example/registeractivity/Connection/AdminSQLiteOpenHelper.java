package com.example.registeractivity.Connection;

import  android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "ManoAmiga.db";
    public AdminSQLiteOpenHelper(Context context) {
        super(context, "ManoAmiga.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DataBases) {
        DataBases.execSQL("create table users(username text primary key, name text, lastname text, email text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DataBases, int oldVersion, int newVersion) {
        DataBases.execSQL("drop table if exists users");
    }

    public Boolean insertData(String username,String name, String email, String password){
        SQLiteDatabase DataBases = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = DataBases.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase DataBases = this.getWritableDatabase();
        Cursor cursor = DataBases.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery
                ("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}