package com.anggitprayogo.dicoding.cataloguemovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String TABLE_FAVOURITE = "favourites";

    public static class COLUMN_FAVOURITE implements BaseColumns{

        public final static String RELEASE_DATE =  "release_date";
        public final static String OVERVIEW = "overview";
        public final static String BACKDROP_PATH = "backdroppath";
        public final static String POSTER_PATH = "poster_path";
        public final static String POPULARITY = "popularity";
        public final static String TITLE = "title";
        public final static String VOTE_AVARAGE = "vote_avarage";
        public final static String VOTE_COUNT = "vote_count";
        public final static String ID = "id";
    }

    public static final String AUTHORITY = "com.anggitprayogo.dicoding.cataloguemovie";

    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVOURITE)
            .build();

    public static final String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static final int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static final long getColumnLong(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

}
