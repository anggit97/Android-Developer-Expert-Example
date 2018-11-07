package com.anggitprayogo.dicoding.notificationexample;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID = 100;
    private NotificationCompat.Builder builder;
    private Handler handler = new Handler();
    private Class aClass =  NotificationService.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startService(new Intent(getApplicationContext(), aClass));
        }
    };

    public void sendNotification(View view) {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

//        handler.postDelayed(runnable, 5000);

        startService(new Intent(this, NotificationService.class));

    }
}
