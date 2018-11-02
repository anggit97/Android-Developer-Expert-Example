package com.anggitprayogo.dicoding.cataloguemovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "movie_db";
    public static final int DATABASE_VERSION = 2;
    private Context context;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("create table %s(" +
            "%s integer primary key autoincrement," +
            "%s text not null," +
            "%s text nullable," +
            "%s text nullable," +
            "%s text nullable," +
            "%s text nullable," +
            "%s text nullable," +
            "%s text nullable," +
            "%s text nullable," +
            "%s text nullable);",
            DatabaseContract.TABLE_FAVOURITE,
            DatabaseContract.COLUMN_FAVOURITE._ID,
            DatabaseContract.COLUMN_FAVOURITE.ID,
            DatabaseContract.COLUMN_FAVOURITE.TITLE,
            DatabaseContract.COLUMN_FAVOURITE.BACKDROP_PATH,
            DatabaseContract.COLUMN_FAVOURITE.POSTER_PATH,
            DatabaseContract.COLUMN_FAVOURITE.RELEASE_DATE,
            DatabaseContract.COLUMN_FAVOURITE.POPULARITY,
            DatabaseContract.COLUMN_FAVOURITE.VOTE_AVARAGE,
            DatabaseContract.COLUMN_FAVOURITE.VOTE_COUNT,
            DatabaseContract.COLUMN_FAVOURITE.OVERVIEW
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_FAVOURITE);
        onCreate(db);
    }
}
