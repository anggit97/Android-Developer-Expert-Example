package com.anggitprayogo.dicoding.kamusoffline.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import com.anggitprayogo.dicoding.kamusoffline.entity.Kamus;

import java.util.ArrayList;

public class KamusHelper {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLiteException{
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }


    public void close(){
        sqLiteDatabase.close();
    }


    /**
     *
     * @param word
     * @param type = ada 2 jenis,
     *             0, id ke en
     *             1, en ke id
     * @return
     */
    public ArrayList<Kamus> getKosakataByWord(String word, int type){
        ArrayList<Kamus> kamuses = new ArrayList<>();
        String tableSelected = (type == 0) ? DatabaseContract.TABLE_ID_TO_EN : DatabaseContract.TABLE_EN_TO_ID;
        Cursor cursor = sqLiteDatabase.query(
                tableSelected,
                null,
                DatabaseContract.TableKamus.KOSAKATA+" LIKE ?",
                new String[]{"%"+word+"%"},
                null,
                null,
                DatabaseContract.TableKamus._ID+ " ASC");

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                Kamus kamus = new Kamus();
                kamus.setKosakata(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableKamus.KOSAKATA)));
                kamus.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableKamus.DESKRIPSI)));
                kamuses.add(kamus);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return kamuses;
    }

    /**
     *
     * @param kamus
     * @param type = ada 2 jenis,
     *             0, id ke en
     *             1, en ke id
     */
    public void insertTransaction(Kamus kamus, int type){
        String tableSelected = (type == 0) ? DatabaseContract.TABLE_ID_TO_EN : DatabaseContract.TABLE_EN_TO_ID;
        String sql = "insert into "+tableSelected+ "("+ DatabaseContract.TableKamus.KOSAKATA+","+ DatabaseContract.TableKamus.DESKRIPSI+")" +
                "values(?,?);";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.bindString(1, kamus.getKosakata());
        sqLiteStatement.bindString(2, kamus.getDeskripsi());
        sqLiteStatement.execute();
        sqLiteStatement.clearBindings();
    }

    public void beginTransaction(){
        sqLiteDatabase.beginTransaction();
    }

    public void setTransaction(){
        sqLiteDatabase.setTransactionSuccessful();
    }

    public void endTransaction(){
        sqLiteDatabase.endTransaction();
    }
}
