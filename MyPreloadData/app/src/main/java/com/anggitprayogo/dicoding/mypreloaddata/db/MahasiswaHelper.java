package com.anggitprayogo.dicoding.mypreloaddata.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import com.anggitprayogo.dicoding.mypreloaddata.entity.Mahasiswa;

import java.util.ArrayList;

public class MahasiswaHelper {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public MahasiswaHelper(Context context) {
        this.context = context;
    }

    public MahasiswaHelper open() throws SQLiteException{
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Mahasiswa> getDataByName(String nama){
        ArrayList<Mahasiswa> mahasiswas = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(
                DatabaseContract.TABLE_MAHASISWA,
                null,
                DatabaseContract.ColumnMahasiswa.NAMA+" LIKE ?",
                new String[]{nama},
                null,
                null,
                DatabaseContract.ColumnMahasiswa._ID+" ASC");
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            do {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setNama(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ColumnMahasiswa.NAMA)));
                mahasiswa.setNim(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ColumnMahasiswa.NIM)));
                mahasiswas.add(mahasiswa);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }

        cursor.close();
        return mahasiswas;
    }

    public ArrayList<Mahasiswa> getAllData(){
        ArrayList<Mahasiswa> mahasiswas = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(
                DatabaseContract.TABLE_MAHASISWA,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.ColumnMahasiswa._ID+" ASC");
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            do {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setNama(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ColumnMahasiswa.NAMA)));
                mahasiswa.setNim(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ColumnMahasiswa.NIM)));
                mahasiswas.add(mahasiswa);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }

        cursor.close();
        return mahasiswas;
    }

    public long insert(Mahasiswa mahasiswa){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.ColumnMahasiswa.NAMA, mahasiswa.getNama());
        contentValues.put(DatabaseContract.ColumnMahasiswa.NIM, mahasiswa.getNim());
        return sqLiteDatabase.insert(DatabaseContract.TABLE_MAHASISWA, null, contentValues);
    }

    public void begintransaction(){
        sqLiteDatabase.beginTransaction();
    }

    public void setTransactionSuccess(){
        sqLiteDatabase.setTransactionSuccessful();
    }

    public void endTransactionSuccess(){
        sqLiteDatabase.endTransaction();
    }

    public void insertTransaction(Mahasiswa mahasiswa){
        String sql = "INSERT INTO "+DatabaseContract.TABLE_MAHASISWA+"("+DatabaseContract.ColumnMahasiswa.NAMA+","+DatabaseContract.ColumnMahasiswa.NIM+")" +
                "VALUES(?,?);";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.bindString(1, mahasiswa.getNama());
        sqLiteStatement.bindString(2, mahasiswa.getNim());
        sqLiteStatement.execute();
        sqLiteStatement.clearBindings();
    }

    public int update(Mahasiswa mahasiswa){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.ColumnMahasiswa.NAMA, mahasiswa.getNama());
        contentValues.put(DatabaseContract.ColumnMahasiswa.NIM, mahasiswa.getNim());
        return sqLiteDatabase.update(DatabaseContract.TABLE_MAHASISWA, contentValues, DatabaseContract.ColumnMahasiswa._ID+"='"+mahasiswa.getId()+"'", null);
    }

    public void delete(int id){
        sqLiteDatabase.delete(DatabaseContract.TABLE_MAHASISWA, DatabaseContract.ColumnMahasiswa._ID+"='"+id+"'", null);
    }

}
