package com.anggitprayogo.dicoding.mynotesapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "mynotes";
    private static final int DATABASE_VERSION = 1;
    private static final String QUERY_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s(" +
            "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%s TEXT NOT NULL," +
            "%s TEXT NOT NULL," +
            "%s TEXT NOT NULL);",
            DatabaseContract.TABLE_NOTES,
            DatabaseContract.NOTE_COLUMNS._ID,
            DatabaseContract.NOTE_COLUMNS.TITLE,
            DatabaseContract.NOTE_COLUMNS.DESCRIPTION,
            DatabaseContract.NOTE_COLUMNS.DATE);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_NOTES);
        onCreate(db);
    }
}
