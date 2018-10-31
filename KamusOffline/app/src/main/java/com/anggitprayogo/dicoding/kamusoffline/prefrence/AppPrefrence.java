package com.anggitprayogo.dicoding.kamusoffline.prefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPrefrence {

    private final String PREF_NAME = "kamus_pref";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    public static final String EXTRA_IS_APP_FIRST_RUN = "extras_is_app_first_run";

    public AppPrefrence(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public boolean isAppFirstRun(){
        return sharedPreferences.getBoolean(EXTRA_IS_APP_FIRST_RUN, true);
    }

    public void setAppFirstRun(boolean input){
        editor.putBoolean(EXTRA_IS_APP_FIRST_RUN, input);
        editor.apply();
    }
}
