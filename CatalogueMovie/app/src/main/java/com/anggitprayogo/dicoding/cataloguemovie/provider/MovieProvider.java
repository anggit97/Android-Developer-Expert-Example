package com.anggitprayogo.dicoding.cataloguemovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.anggitprayogo.dicoding.cataloguemovie.db.DatabaseContract;
import com.anggitprayogo.dicoding.cataloguemovie.db.MovieHelper;

public class MovieProvider extends ContentProvider{

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher urimatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        urimatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_FAVOURITE, MOVIE);

        urimatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_FAVOURITE+"/#", MOVIE_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (urimatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.query();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
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

        long added = 0;

        switch (urimatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insert(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(DatabaseContract.CONTENT_URI+"/"+added);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted = 0;
        switch (urimatcher.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.delete(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated = 0;
        switch (urimatcher.match(uri)){
            case MOVIE_ID:
                updated = movieHelper.update(values, uri.getLastPathSegment());
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }
}
