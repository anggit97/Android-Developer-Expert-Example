package com.anggitprayogo.dicoding.myweatherloader;

import android.util.Log;

import org.json.JSONObject;

import java.text.DecimalFormat;

public class WheaterItems {

    private final String TAG = getClass().getSimpleName();

    final String APP_ID = "827ccc94b9d80b6937cae986bc675e5e";
    final String CITY = "Jakarta";
    String url = "http://api.openweathermap.org/data/2.5/weather?q="+CITY+"&appid="+APP_ID;


    private int id;
    private String nama;
    private String currentWheater;
    private String description;
    private String temprature;


    public WheaterItems(JSONObject jsonObject){

        try {

            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String currentWeather = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

            double kelvinTemp = jsonObject.getJSONObject("main").getDouble("temp");
            double celTemp = kelvinTemp - 273;
            String temp = new DecimalFormat("##.##").format(celTemp);

            this.id = id;
            this.nama = name;
            this.currentWheater = currentWeather;
            this.description = description;

        }catch (Exception e){
            Log.d(TAG, "WheaterItems: "+e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCurrentWheater() {
        return currentWheater;
    }

    public void setCurrentWheater(String currentWheater) {
        this.currentWheater = currentWheater;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }
}
