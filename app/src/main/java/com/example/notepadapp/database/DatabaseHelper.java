package com.example.notepadapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION=1;
    public static final String DATABASE_NAME="Note.db";
    public static final String USER_TABLE="user";
    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    public static final String NOTE_TABLE="note";
    public static final String ID="_id";
    public static final String DATE="date";
    public static final String CONTENT="content";
    public static final String PHOTO="photo";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //new table for saving user information
        sqLiteDatabase.execSQL(" CREATE TABLE " + USER_TABLE +" ( "
                + USERNAME + " TEXT PRIMARY KEY,"
                + PASSWORD +" TEXT )");
        //new table for saving the note detail
        sqLiteDatabase.execSQL("CREATE TABLE " + NOTE_TABLE +" ( "
                + ID + " INTEGER PRIMARY KEY,"
                + USERNAME +" TEXT, "
                + CONTENT + " TEXT, "
                + PHOTO + " TEXT, "
                + DATE +" TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //allow add new data in the column
        sqLiteDatabase.execSQL("alter table "+ USER_TABLE+" add column other string");
        sqLiteDatabase.execSQL("alter table "+ NOTE_TABLE+" add column other string");

    }
}
