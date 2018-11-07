package com.anggitprayogo.dicoding.mynotesapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.anggitprayogo.dicoding.mynotesapp.entity.Note;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NoteHelper {

    private static String DATABASE_TABLE = DatabaseContract.TABLE_NOTES;
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
        Cursor cursor = database.query(DatabaseContract.TABLE_NOTES, null, null, null, null, null, DatabaseContract.NOTE_COLUMNS._ID+" ASC");
        cursor.moveToFirst();
        Note note;

        if (cursor.getCount() > 0){
            do {
                note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.NOTE_COLUMNS._ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NOTE_COLUMNS.TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NOTE_COLUMNS.DESCRIPTION)));
                note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NOTE_COLUMNS.DATE)));
                notes.add(note);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }

        cursor.close();
        return notes;
    }

    public Note show(int id){
        ArrayList<Note> notes = new ArrayList<>();
        Note note = new Note();
        Cursor cursor = database.query(DatabaseContract.TABLE_NOTES, null, DatabaseContract.NOTE_COLUMNS._ID+"='"+id+"'", null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0){
            do {
                Note detailNote = new Note();
                detailNote.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NOTE_COLUMNS.TITLE)));
                detailNote.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NOTE_COLUMNS.DESCRIPTION)));
                detailNote.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NOTE_COLUMNS.DATE)));
                notes.add(detailNote);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());

            cursor.close();
            return notes.get(0);
        }else{
            cursor.close();
            return null;
        }
    }

    public long insert(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.NOTE_COLUMNS.TITLE, note.getTitle());
        contentValues.put(DatabaseContract.NOTE_COLUMNS.DESCRIPTION, note.getDescription());
        contentValues.put( DatabaseContract.NOTE_COLUMNS.DATE, note.getDate());
        return database.insert(DatabaseContract.TABLE_NOTES, null, contentValues);
    }

    public int update(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.NOTE_COLUMNS.TITLE, note.getTitle());
        contentValues.put(DatabaseContract.NOTE_COLUMNS.DESCRIPTION, note.getDescription());
        contentValues.put( DatabaseContract.NOTE_COLUMNS.DATE, note.getDate());
        return database.update(DatabaseContract.TABLE_NOTES, contentValues, DatabaseContract.NOTE_COLUMNS._ID+"='"+note.getId()+"'",null);
    }

    public int delete(int id){
        return database.delete(DatabaseContract.TABLE_NOTES, DatabaseContract.NOTE_COLUMNS._ID+"='"+id+"'",null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DatabaseContract.TABLE_NOTES, null,
                DatabaseContract.NOTE_COLUMNS._ID+"=?",
                new String[]{id},
                null,
                null,
                null);
    }

    public Cursor queryProvider(){
        return database.query(DatabaseContract.TABLE_NOTES,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.NOTE_COLUMNS._ID+ " DESC");
    }

    public long insertProvider(ContentValues contentValues){
        return database.insert(DatabaseContract.TABLE_NOTES, null, contentValues);
    }

    public int updateProvider(ContentValues contentValues, String id){
        return database.update(DatabaseContract.TABLE_NOTES, contentValues, DatabaseContract.NOTE_COLUMNS._ID +"=?",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DatabaseContract.TABLE_NOTES, DatabaseContract.NOTE_COLUMNS._ID+"=?", new String[]{id});
    }
}
