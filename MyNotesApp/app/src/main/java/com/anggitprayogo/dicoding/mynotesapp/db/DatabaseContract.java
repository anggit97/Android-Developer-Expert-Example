package com.anggitprayogo.dicoding.mynotesapp.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static final String TABLE_NOTES = "notes";

    static final class NOTE_COLUMNS implements BaseColumns{

        static final String TITLE = "title";
        static final String DESCRIPTION = "description";
        static final String DATE = "date";
    }

}
