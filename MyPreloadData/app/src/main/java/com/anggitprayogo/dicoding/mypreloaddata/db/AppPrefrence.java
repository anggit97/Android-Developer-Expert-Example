package com.anggitprayogo.dicoding.mypreloaddata.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.anggitprayogo.dicoding.mypreloaddata.R;

public class AppPrefrence {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public AppPrefrence(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void setFirstRun(Boolean input){
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key, input);
        editor.apply();
    }

    public Boolean getFirstRun(){
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.app_first_run), true);
    }
}
