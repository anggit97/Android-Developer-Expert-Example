package com.anggitprayogo.dicoding.cataloguemovie.reciever;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.anggitprayogo.dicoding.cataloguemovie.BuildConfig;
import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.feature.home.HomeActivtiy;
import com.anggitprayogo.dicoding.cataloguemovie.model.HomeMovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.EndpointClient;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmReciever extends BroadcastReceiver{

    private final String TAG = getClass().getSimpleName();
    public static final String EXTRA_MESSAGE = "extra_message";
    public static final String EXTRA_TYPE = "extra_type";
    public static final String DAILY_REMAINDER_TYPE = "daily_remainder_type";
    public static final String RELEASE_REMAINDER_TYPE = "release_remainder_type";
    public static final int NOTIF_DAILY_REMAINDER = 100;
    public static final int NOTIF_RELEASE_REMAINDER = 101;
    private List<HomeMovieResponse.Results> results = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getExtras() != null){
            String message = intent.getExtras().getString(EXTRA_MESSAGE);
            String type = intent.getExtras().getString(EXTRA_TYPE);
            int notifId = type.equalsIgnoreCase(DAILY_REMAINDER_TYPE) ? NOTIF_DAILY_REMAINDER : NOTIF_RELEASE_REMAINDER;

            if (notifId == NOTIF_DAILY_REMAINDER){
                showNotifications(context, notifId, message);
            }else{
                fetchReleaseMovie(context);
            }

        }else{
            Toast.makeText(context, "none", Toast.LENGTH_SHORT).show();
        }

    }

    private void fetchReleaseMovie(final Context context) {
        results.clear();
        final Context mContext = context;
        Call<HomeMovieResponse> call = ServiceGenerator.createService(EndpointClient.class, context)
                .nowPlaying(BuildConfig.OPEN_WEATHER_MAP_API_KEY, ServiceGenerator.LANG);
        call.enqueue(new Callback<HomeMovieResponse>() {
            @Override
            public void onResponse(Call<HomeMovieResponse> call, Response<HomeMovieResponse> response) {

                results.addAll(response.body().getResults());

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = simpleDateFormat.format(date);

                int i = 0;
                int notifId = 0;
                while (i < results.size()) {

                    if (notifId > 5){
                        break;
                    }

                    if (results.get(i).getReleaseDate().equalsIgnoreCase(currentDate)){
                        if (notifId < 5){
                            showNotificationRelease(mContext, results.get(i).getTitle(),notifId);
                        }else{
                            break;
                        }
                        notifId++;
                    }
                    i++;
                }

            }

            @Override
            public void onFailure(Call<HomeMovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void showNotificationRelease(Context context, String title, int l) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = null;

        intent = new Intent(context, HomeActivtiy.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 123, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Release terbaru hari ini film "+title)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000,1000,1000,1000});

        notificationManager.notify(l, builder.build());
    }

    public void showNotifications(Context context, int notifId, String message) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = null;

        intent = new Intent(context, HomeActivtiy.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 123, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000,1000,1000,1000});

        notificationManager.notify(notifId, builder.build());
    }

    public void startDailyRemainder(Context context, String type, String time, String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReciever.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String[] arrTime = time.split(":");
        int hour = Integer.parseInt(arrTime[0]);
        int minute = Integer.parseInt(arrTime[1]);
        int second = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        int requestCode = NOTIF_DAILY_REMAINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, R.string.message_daily_remainder_on, Toast.LENGTH_SHORT).show();
    }

    public void cancelDailyRemainder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReciever.class);
        int requestCode = NOTIF_DAILY_REMAINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, R.string.message_cancle_daily_remainder, Toast.LENGTH_SHORT).show();
    }

    public void startReleaseRemainder(Context context, String type, String  time, String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReciever.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String[] arrTime = time.split(":");
        int hour = Integer.parseInt(arrTime[0]);
        int minute = Integer.parseInt(arrTime[1]);
        int second = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        int requestCode = NOTIF_RELEASE_REMAINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, R.string.message_release_remainder_on, Toast.LENGTH_SHORT).show();
    }

    public void cancelReleaseRemainder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReciever.class);
        int requestCode = NOTIF_RELEASE_REMAINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, R.string.message_cancle_release_remainder, Toast.LENGTH_SHORT).show();
    }

}
