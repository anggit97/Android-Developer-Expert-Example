package com.anggitprayogo.dicoding.mynotesapp.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.anggitprayogo.dicoding.mynotesapp.db.DatabaseContract;

public class Note implements Parcelable {

    private int id;
    private String title;
    private String description;
    private String date;

    public Note(Cursor cursor){
        this.id = DatabaseContract.getColumnInt(cursor, DatabaseContract.NOTE_COLUMNS._ID);
        this.title = DatabaseContract.getColumnString(cursor, DatabaseContract.NOTE_COLUMNS.TITLE);
        this.description = DatabaseContract.getColumnString(cursor, DatabaseContract.NOTE_COLUMNS.DESCRIPTION);
        this.date = DatabaseContract.getColumnString(cursor, DatabaseContract.NOTE_COLUMNS.DATE);
    }

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
    }

    protected Note(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
