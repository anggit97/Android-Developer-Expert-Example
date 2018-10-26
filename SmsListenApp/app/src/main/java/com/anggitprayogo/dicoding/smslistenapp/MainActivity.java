package com.anggitprayogo.dicoding.smslistenapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anggitprayogo.dicoding.smslistenapp.service.DownloadService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDownload;
    public static final String ACTION_DOWNLOAD_STATUS = "action_download_status";
    private BroadcastReceiver downloadReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Incoming Message");
        btnDownload = findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);

        downloadReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Download Selesai.", Toast.LENGTH_SHORT).show();
            }
        };

        IntentFilter intentFilterDownload = new IntentFilter(ACTION_DOWNLOAD_STATUS);
        registerReceiver(downloadReciever, intentFilterDownload);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downloadReciever != null){
            unregisterReceiver(downloadReciever);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_download:
                Intent downloadIntent = new Intent(this,DownloadService.class);
                startService(downloadIntent);
                break;
        }
    }
}
