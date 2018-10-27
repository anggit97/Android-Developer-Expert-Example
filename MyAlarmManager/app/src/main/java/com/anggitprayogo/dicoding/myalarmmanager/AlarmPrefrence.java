package com.anggitprayogo.dicoding.myalarmmanager;

import android.content.Context;
import android.content.SharedPreferences;

public class AlarmPrefrence {

    private final String PREF_NAME = "pref_name";
    private final String KEY_ONE_TIME_DATE = "key_one_time_date";
    private final String KEY_ONE_TIME_TIME = "key_one_time_time";
    private final String KEY_ONE_TIME_MESSAGE = "key_one_time_message";
    private final String KEY_REPEATING_TIME = "key_repeating_time";
    private final String KEY_REPEATING_MESSAGE = "key_repeating_message";
    private SharedPreferences mSharedPrefrences;
    private SharedPreferences.Editor editor;

    public AlarmPrefrence(Context context){
        mSharedPrefrences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPrefrences.edit();
    }

    public String getKEY_ONE_TIME_DATE() {
        return mSharedPrefrences.getString(KEY_ONE_TIME_DATE, "");
    }

    public void setKEY_ONE_TIME_DATE(String date){
        editor.putString(KEY_ONE_TIME_DATE, date);
        editor.commit();
    }

    public String getKEY_ONE_TIME_TIME() {
        return mSharedPrefrences.getString(KEY_ONE_TIME_TIME, "");
    }

    public void setKEY_ONE_TIME_TIME(String time){
        editor.putString(KEY_ONE_TIME_TIME, time);
        editor.commit();
    }

    public String getKEY_ONE_TIME_MESSAGE() {
        return mSharedPrefrences.getString(KEY_ONE_TIME_MESSAGE, "");
    }

    public void setKEY_ONE_TIME_MESSAGE(String message){
        editor.putString(KEY_ONE_TIME_MESSAGE, message);
        editor.commit();
    }

    public String getKEY_REPEATING_TIME() {
        return mSharedPrefrences.getString(KEY_REPEATING_TIME, "");
    }

    public void setKEY_REPEATING_TIME(String time){
        editor.putString(KEY_REPEATING_TIME, time);
        editor.commit();
    }

    public String getKEY_REPEATING_MESSAGE() {
        return mSharedPrefrences.getString(KEY_REPEATING_MESSAGE, "");
    }

    public void setKEY_REPEATING_MESSAGE(String message){
        editor.putString(KEY_REPEATING_MESSAGE, message);
        editor.commit();
    }

    public void onClear(){
        editor.clear();
        editor.commit();
    }
}
