package com.anggitprayogo.dicoding.smslistenapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.anggitprayogo.dicoding.smslistenapp.MainActivity;

public class DownloadService extends IntentService {

    private final String TAG = getClass().getSimpleName();

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        if (intent != null) {
            try {
                Thread.sleep(5000);
            }catch (Exception e){
                Log.d(TAG, "onHandleIntent: "+e.getMessage());
            }

            Intent notifyFinishIntent = new Intent(MainActivity.ACTION_DOWNLOAD_STATUS);
            sendBroadcast(notifyFinishIntent);
        }
    }

}
