package com.anggitprayogo.dicoding.kamusoffline.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static final String TABLE_ID_TO_EN = "id_to_en";
    static final String TABLE_EN_TO_ID = "en_to_id";

    static class TableKamus implements BaseColumns{
        static final String KOSAKATA = "kosakata";
        static final String DESKRIPSI = "deskripsi";
    }
}
