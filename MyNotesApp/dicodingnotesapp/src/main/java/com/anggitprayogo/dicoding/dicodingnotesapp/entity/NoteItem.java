package com.anggitprayogo.dicoding.dicodingnotesapp.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.anggitprayogo.dicoding.dicodingnotesapp.db.DatabaseContract;

import static com.anggitprayogo.dicoding.dicodingnotesapp.db.DatabaseContract.getColumnInt;
import static com.anggitprayogo.dicoding.dicodingnotesapp.db.DatabaseContract.getColumnString;

public class NoteItem implements Parcelable {
    private int id;
    private String title, description, date;

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

    public NoteItem() {
    }

    public NoteItem(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.NOTE_COLUMNS._ID);
        this.title = getColumnString(cursor, DatabaseContract.NOTE_COLUMNS.TITLE);
        this.description = getColumnString(cursor, DatabaseContract.NOTE_COLUMNS.DESCRIPTION);
        this.date = getColumnString(cursor, DatabaseContract.NOTE_COLUMNS.DATE);
    }

    protected NoteItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<NoteItem> CREATOR = new Parcelable.Creator<NoteItem>() {
        @Override
        public NoteItem createFromParcel(Parcel source) {
            return new NoteItem(source);
        }


        @Override
        public NoteItem[] newArray(int size) {
            return new NoteItem[size];
        }
    };
}