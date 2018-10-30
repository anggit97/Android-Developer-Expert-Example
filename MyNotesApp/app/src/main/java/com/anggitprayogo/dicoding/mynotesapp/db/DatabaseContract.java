package com.anggitprayogo.dicoding.mynotesapp.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_NOTE = "note";

    static final class NoteColumns implements BaseColumns{

        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String DATE = "date";
    }
}
