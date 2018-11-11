package com.anggitprayogo.dicoding.cataloguemovie.prefrence;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceUtil {

    private static final String PREF_NAME = "catalogue_movie_core";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String DAILY_REMAINDER = "daily_remainder";
    private static final String RELEASE_REMAINDER = "release_remainder";
    private static final String DAILY_DATE = "daily_date";
    private static final String RELEASE_DATE = "release_date";

    public PrefrenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setDailyRemainder(Boolean bool){
        editor.putBoolean(DAILY_REMAINDER, bool).apply();
    }

    public Boolean getDailyRemainder(){
        return sharedPreferences.getBoolean(DAILY_REMAINDER, true);
    }

    public void setReleaseRemainder(Boolean bool){
        editor.putBoolean(RELEASE_REMAINDER, bool).apply();
    }

    public Boolean getReleaseRemainder(){
        return sharedPreferences.getBoolean(RELEASE_REMAINDER, true);
    }

    public void setDailyDate(String dailyDate){
        editor.putString(DAILY_DATE, dailyDate).apply();
    }

    public String getDailyDate(){
        return sharedPreferences.getString(DAILY_DATE, "");
    }

    public void setReleaseDate(String releaseDate){
        editor.putString(RELEASE_DATE, releaseDate).apply();
    }

    public String getReleaseDate(){
        return sharedPreferences.getString(RELEASE_DATE, "");
    }

    public boolean isDailyRemainderExist(){
        return sharedPreferences.contains(DAILY_REMAINDER);
    }

    public boolean isReleaseRemainderExists(){
        return sharedPreferences.contains(RELEASE_REMAINDER);
    }

}
