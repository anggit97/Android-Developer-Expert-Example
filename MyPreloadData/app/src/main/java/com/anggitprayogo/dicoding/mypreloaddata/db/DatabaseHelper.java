package com.anggitprayogo.dicoding.mypreloaddata.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "mahasiswa_db";
    private static final int DATABASE_VERSION = 1;
    private static final String QUERY_CREATE_TABLE_MAHASISWA = String.format("create table %s" +
            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%s TEXT NOT NULL," +
            "%s TEXT NOT NULL);",
            DatabaseContract.TABLE_MAHASISWA,
            DatabaseContract.ColumnMahasiswa._ID,
            DatabaseContract.ColumnMahasiswa.NAMA,
            DatabaseContract.ColumnMahasiswa.NIM);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE_MAHASISWA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_MAHASISWA);
        onCreate(db);
    }
}
