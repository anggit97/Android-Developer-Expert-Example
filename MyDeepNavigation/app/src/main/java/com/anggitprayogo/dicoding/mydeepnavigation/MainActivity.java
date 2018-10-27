package com.anggitprayogo.dicoding.mydeepnavigation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = getClass().getSimpleName();

    private Button btnOpenDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenDetail = findViewById(R.id.btn_open_detail);
        btnOpenDetail.setOnClickListener(this);

        DelayAsyn delayAsyn = new DelayAsyn();
        delayAsyn.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open_detail:
                Intent openDetail = new Intent(this, DetailActivity.class);
                startActivity(openDetail);
                break;
        }
    }

    class DelayAsyn extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(5000);
            }catch (Exception e){
                Log.d(TAG, "doInBackground: "+e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showNotification();
        }
    }

    private void showNotification() {

        String title = "Ini message dari pesannya";
        String message = "ini judul dari pesanya";

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TITLE, title);
        intent.putExtra(DetailActivity.EXTRA_MESSAGE, message);

        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addParentStack(DetailActivity.class)
                .addNextIntent(intent)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[]{1000,1000,1000,1000})
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(110, builder.build());

    }
}
