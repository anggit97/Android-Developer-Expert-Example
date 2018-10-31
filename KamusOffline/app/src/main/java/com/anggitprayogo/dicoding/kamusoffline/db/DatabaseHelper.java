package com.anggitprayogo.dicoding.kamusoffline.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "kamus_db";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    private static final String QUERY_CREATE_TABLE_ID_TO_EN = String.format("create table %s(" +
            "%s integer primary key autoincrement," +
            "%s text not null," +
            "%s text not null);",
            DatabaseContract.TABLE_ID_TO_EN,
            DatabaseContract.TableKamus._ID,
            DatabaseContract.TableKamus.KOSAKATA,
            DatabaseContract.TableKamus.DESKRIPSI);

    private static final String QUERY_CREATE_TABLE_EN_TO_ID = String.format("create table %s(" +
                    "%s integer primary key autoincrement," +
                    "%s text not null," +
                    "%s text not null);",
            DatabaseContract.TABLE_EN_TO_ID,
            DatabaseContract.TableKamus._ID,
            DatabaseContract.TableKamus.KOSAKATA,
            DatabaseContract.TableKamus.DESKRIPSI);


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE_EN_TO_ID);
        db.execSQL(QUERY_CREATE_TABLE_ID_TO_EN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_EN_TO_ID);
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_ID_TO_EN);
        onCreate(db);
    }
}
