package com.anggitprayogo.dicoding.mytestingapp;

public class MainPresenter {

    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public double volume(double panjang, double lebar, double tinggi){
        return panjang * lebar * tinggi;
    }

    public void hitungVolume(double panjang, double lebar, double tinggi){
        double volume = volume(panjang, lebar, tinggi);
        mainView.tampilkanVolume(new MainModel(volume));
    }
}
