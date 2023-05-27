package com.example.fabset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLite extends SQLiteOpenHelper {
    private final String Login_Table = "Login";
    private final String Username="username";
    private final String Password="password";
    private final String Email="email";
    private final String Address="address";
    private final String Phone="phone";
    private final String Image="profile_image";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Fabset";
    public SQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateLoginTable = "CREATE TABLE "+Login_Table+
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Username+" TEXT NOT NULL UNIQUE,"+
                Password+" TEXT," +
                Email+" TEXT UNIQUE," +
                Address+" TEXT," +
                Phone+" TEXT," +
                Image+"TEXT)";
        sqLiteDatabase.execSQL(CreateLoginTable);
    }
    public User SearchUser(String name ,String password)
    {
        User user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {name, password};
        Cursor cursor= db.rawQuery("Select * from Login WHERE Username = ? AND Password = ?", selectionArgs);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String pass = cursor.getString(2);
            String email = cursor.getString(3);
            String address = cursor.getString(4);
            String phone = cursor.getString(5);
            user = new User(id, username, pass, email, address, phone);
        }
        cursor.close();
        return user;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ Login_Table);
        onCreate(sqLiteDatabase);
    }

    public long SaveSignupData(String Name, String Pass, String Email, String Address,String Phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", Name);
        cv.put("Password",Pass);
        cv.put("Email", Email);
        cv.put("Address", Address);
        cv.put("Phone", Phone);
        long r = db.insert(Login_Table,null,cv);
        return r;
    }

    public String ViewData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Login", null);
        String record="";
        while (cursor.moveToNext())
        {
            String uname = cursor.getString(1);
            String upass = cursor.getString(2);

            record = record+uname+"-"+upass+"\n";
        }
        return record;
    }


    public boolean UpdateData(String name, String pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("username", name);
        cv.put("password", pass);
        long r = 0;
        Cursor cursor = db.rawQuery("Select * from Login where UserName = ?", new String[]{name});
        if(cursor.getCount()>0){
            r=db.update("Login", cv,"UserName = ?", new String[]{name});
        }
        if (r>0)
            return true;
        else
            return false;
    }
}
