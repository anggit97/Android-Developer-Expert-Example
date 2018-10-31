package com.anggitprayogo.dicoding.kamusoffline.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Kamus implements Parcelable {

    private int id;
    private String kosakata;
    private String deskripsi;

    public Kamus(int id, String kosakata, String deskripsi) {
        this.id = id;
        this.kosakata = kosakata;
        this.deskripsi = deskripsi;
    }

    public Kamus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKosakata() {
        return kosakata;
    }

    public void setKosakata(String kosakata) {
        this.kosakata = kosakata;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.kosakata);
        dest.writeString(this.deskripsi);
    }

    protected Kamus(Parcel in) {
        this.id = in.readInt();
        this.kosakata = in.readString();
        this.deskripsi = in.readString();
    }

    public static final Parcelable.Creator<Kamus> CREATOR = new Parcelable.Creator<Kamus>() {
        @Override
        public Kamus createFromParcel(Parcel source) {
            return new Kamus(source);
        }

        @Override
        public Kamus[] newArray(int size) {
            return new Kamus[size];
        }
    };
}
