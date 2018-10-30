package com.anggitprayogo.dicoding.mynotesapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.anggitprayogo.dicoding.mynotesapp.entity.Note;

import java.util.ArrayList;

public class NoteHelper {

    private static String DATABASE_TABLE = DatabaseContract.TABLE_NOTE;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public NoteHelper(Context context) {
        this.context = context;
    }

    public NoteHelper open() throws SQLiteException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Note> query(){
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(
                DATABASE_TABLE, null, null, null,
                null, null, DatabaseContract.NoteColumns._ID +" DESC", null);
        cursor.moveToFirst();
        Note note;
        if (cursor.getCount() > 0){
            do {

                note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION)));
                note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE)));
                notes.add(note);
                cursor.moveToNext();

            }while (!cursor.isAfterLast());
        }

        cursor.close();
        return notes;
    }

    public long insert(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.NoteColumns.TITLE, note.getTitle());
        contentValues.put(DatabaseContract.NoteColumns.DESCRIPTION, note.getDescription());
        contentValues.put(DatabaseContract.NoteColumns.DATE, note.getDescription());
        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public int update(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.NoteColumns.TITLE, note.getTitle());
        contentValues.put(DatabaseContract.NoteColumns.DESCRIPTION, note.getDescription());
        contentValues.put(DatabaseContract.NoteColumns.DATE, note.getDate());
        return database.update(DATABASE_TABLE, contentValues, DatabaseContract.NoteColumns._ID+"='"+note.getId()+"'", null);
    }

    public int delete(int id){
        return database.delete(DATABASE_TABLE, DatabaseContract.NoteColumns._ID+"='"+id+"'",null);
    }
}
