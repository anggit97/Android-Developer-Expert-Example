package com.anggitprayogo.dicoding.mynotesapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.anggitprayogo.dicoding.mynotesapp.db.DatabaseHelper;

public class NoteProvider extends ContentProvider{

    private static final String AUTHORITY = "com.anggitprayogo.dicoding.mynotesapp";
    private static final String BASE_PATH = "notes";
    public static final  Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+BASE_PATH);
    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, NOTES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTES_ID);
    }

    private SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;

        if (uriMatcher.match(uri) == NOTES){
            cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME_NOTE, null, null, null, null, null, null);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME_NOTE, null, values);

        if (id > 0){
            Uri mUri = ContentUris.withAppendedId(CONTENT_URI, id);
            return mUri;
        }

        throw new SQLiteException("Insert failed for uri "+ uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int delCount = 0;
        switch (uriMatcher.match(uri)){
            case NOTES:
                delCount = sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME_NOTE, selection, null);
                break;
            default:
                throw new IllegalArgumentException("This is unknown uri "+uri);
        }
        return delCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updtCount = 0;
        switch (uriMatcher.match(uri)){
            case NOTES:
                updtCount = sqLiteDatabase.update(DatabaseHelper.TABLE_NAME_NOTE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("This is unknown uri "+uri);
        }

        return updtCount;
    }
}
