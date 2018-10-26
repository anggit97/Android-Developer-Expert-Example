package com.anggitprayogo.dicoding.myservice;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class OriginIntentService extends IntentService {

    public static final String EXTRA_DURATION = "extra_duration";
    private final String TAG = getClass().getSimpleName();

    public OriginIntentService() {
        super("OriginIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        if (intent != null){
            int duration = intent.getIntExtra(EXTRA_DURATION, 0);
            Log.d(TAG, "onHandleIntent: "+duration);
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
