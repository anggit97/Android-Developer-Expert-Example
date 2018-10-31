package com.anggitprayogo.dicoding.mypreloaddata.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Mahasiswa implements Parcelable {

    private int id;
    private String nim;
    private String nama;

    public Mahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }

    public Mahasiswa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nim);
        dest.writeString(this.nama);
    }

    protected Mahasiswa(Parcel in) {
        this.id = in.readInt();
        this.nim = in.readString();
        this.nama = in.readString();
    }

    public static final Parcelable.Creator<Mahasiswa> CREATOR = new Parcelable.Creator<Mahasiswa>() {
        @Override
        public Mahasiswa createFromParcel(Parcel source) {
            return new Mahasiswa(source);
        }

        @Override
        public Mahasiswa[] newArray(int size) {
            return new Mahasiswa[size];
        }
    };
}
