package com.anggitprayogo.dicoding.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class MovieHelper {

    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper databaseHelper;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLiteException{
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public Cursor query(){
        return sqLiteDatabase.query(DatabaseContract.TABLE_FAVOURITE, null,
                null, null, null, null, DatabaseContract.COLUMN_FAVOURITE.ID+ " DESC");
    }

    public Cursor queryByIdProvider(String id){
        return sqLiteDatabase.query(DatabaseContract.TABLE_FAVOURITE, null,
                DatabaseContract.COLUMN_FAVOURITE.ID+"=?",new String[]{id},
                null, null, DatabaseContract.COLUMN_FAVOURITE.ID+ " DESC");
    }

    public long insert(ContentValues contentValues){
        return sqLiteDatabase.insert(DatabaseContract.TABLE_FAVOURITE, null, contentValues);
    }

    public int update(ContentValues contentValues, String id){
        return sqLiteDatabase.update(DatabaseContract.TABLE_FAVOURITE,
                contentValues, DatabaseContract.COLUMN_FAVOURITE.ID+"=?",
                new String[]{id});
    }

    public int delete(String id){
        return sqLiteDatabase.delete(DatabaseContract.TABLE_FAVOURITE, DatabaseContract.COLUMN_FAVOURITE.ID+"=?", new String[]{id});
    }

}
