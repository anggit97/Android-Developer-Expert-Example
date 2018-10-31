package com.anggitprayogo.dicoding.mypreloaddata.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static final String TABLE_MAHASISWA = "mahasiswa";

    static final class ColumnMahasiswa implements BaseColumns{

        static final String NIM = "nim";
        static final String NAMA = "nama";

    }

}
