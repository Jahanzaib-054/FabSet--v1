package com.example.fabset;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
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

    public boolean UpdateProfileData(String old_name, String old_em, String name,String pass, String em, String at, String ph, Bitmap img)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("Username", name);
        cv.put("Password", pass);
        cv.put("Email", em);
        cv.put("Address", at);
        cv.put("Phone",ph);
        cv.put("Image", bitmapToBase64(img));
        long r = 0;
        Cursor cursor = db.rawQuery("Select * from Login where UserName = ? AND Email = ?", new String[]{old_name, old_em});
        if(cursor.getCount()>0){
            r=db.update("Login", cv,"UserName = ? AND Email = ?", new String[]{old_name, old_em});
        }
        if (r>0)
            return true;
        else
            return false;
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

    public User SearchForEdit(String name ,String em)
    {
        User user = null;
        Bitmap imageBitmap = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {name, em};
        Cursor cursor= db.rawQuery("Select * from Login WHERE Username = ? AND Email = ?", selectionArgs);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String pass = cursor.getString(2);
            String email = cursor.getString(3);
            String address = cursor.getString(4);
            String phone = cursor.getString(5);
            String imageBase64 = cursor.getString(6);
            if (imageBase64 != null){
                imageBitmap = base64ToBitmap(imageBase64);
            }
            user = new User(id, username, pass, email, address, phone, imageBitmap);
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


//    for image CRUD operations

    public void insertImage(Bitmap imageBitmap) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Image, bitmapToBase64(imageBitmap));
        db.insert(Login_Table, null, values);
    }

    public Bitmap getImage(int imageId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {Image};
        String selection = "ID = ?";
        String[] selectionArgs = {String.valueOf(imageId)};
        Cursor cursor = db.query(Login_Table, columns, selection, selectionArgs, null, null, null);

        Bitmap imageBitmap = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String imageBase64 = cursor.getString(cursor.getColumnIndex(Image));
            imageBitmap = base64ToBitmap(imageBase64);
        }

        cursor.close();
        return imageBitmap;
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String base64String) {
        byte[] imageBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
}
