package com.anggitprayogo.dicoding.myjobscheduler.service;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.anggitprayogo.dicoding.myjobscheduler.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class GetCurrentWheatherJobService extends JobService{

//    private final String TAG = getClass().getSimpleName();
//
//    final String APP_ID = "827ccc94b9d80b6937cae986bc675e5e";
//    final String CITY = "Jakarta";
//
//    @Override
//    public boolean onStartJob(JobParameters params) {
//        Log.d(TAG, "onStartJob: ");
//        getCurrentWeather(params);
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        Log.d(TAG, "onStopJob: ");
//        return true;
//    }
//
//    private void getCurrentWeather(final JobParameters params) {
//        Log.d(TAG, "getCurrentWeather: ");
//        AsyncHttpClient client = new AsyncHttpClient();
//        String url = "http://api.openweathermap.org/data/2.5/weather?q="+CITY+"&appid="+APP_ID;
//        Log.e(TAG, "getCurrentWeather: "+url);
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String result = new String(responseBody);
//                Log.d(TAG, "onSuccess: "+result);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    String currentWheather = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
//                    String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
//                    double temp = jsonObject.getJSONObject("main").getDouble("temp");
//
//                    double celcius = temp - 273;
//                    String temperature = new DecimalFormat("##.##").format(celcius);
//
//                    String title = "Current Weather";
//                    String message = currentWheather.concat(", ").concat(description).concat(" with ").concat(temperature).concat(" Celcius");
//                    int notifId = 100;
//
//                    showNotification(getApplicationContext(), title, message, notifId);
//
//                    jobFinished(params, false);
//
//                }catch (Exception e){
//                    Log.d(TAG, "onSuccess: "+e.getMessage());
//                    jobFinished(params, true);
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.d(TAG, "onFailure: "+error.getMessage());
//                jobFinished(params, true);
//            }
//        });
//    }
//
//    private void showNotification(Context applicationContext, String title, String message, int notifId) {
//        NotificationManager notificationManager = (NotificationManager)applicationContext.getSystemService(NOTIFICATION_SERVICE);
//
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setVibrate(new long[]{1000,1000,1000,100})
//                .setContentText(message)
//                .setContentTitle(title)
//                .setSmallIcon(R.drawable.ic_wb_sunny_black_24dp)
//                .setSound(alarmSound);
//
//        notificationManager.notify(notifId, builder.build());
//    }

    public static final String TAG = GetCurrentWheatherJobService.class.getSimpleName();
    final String APP_ID = "827ccc94b9d80b6937cae986bc675e5e";
    //private final String CITY = "ISIKAN DENGAN NAMA KOTA KAMU";
    final String CITY = "Jakarta";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob() Executed");
        getCurrentWeather(params);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob() Executed");
        return true;
    }

    private void getCurrentWeather(final JobParameters job){
        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+CITY+"&appid="+APP_ID;
        Log.e(TAG, "getCurrentWeather: "+url );
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    String currentWeather = responseObject.getJSONArray("weather").getJSONObject(0).getString("main");
                    String description = responseObject.getJSONArray("weather").getJSONObject(0).getString("description");
                    double tempInKelvin = responseObject.getJSONObject("main").getDouble("temp");
                    double tempInCelcius = tempInKelvin - 273;
                    String temprature = new DecimalFormat("##.##").format(tempInCelcius);
                    String title = "Current Weather";
                    String message = currentWeather +", "+description+" with "+temprature+" celcius";
                    int notifId = 100;
                    showNotification(getApplicationContext(), title, message, notifId);
                    jobFinished(job, false);
                }catch (Exception e){
                    jobFinished(job, true);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // ketika proses gagal, maka jobFinished diset dengan parameter true. Yang artinya job perlu di reschedule
                jobFinished(job, true);
            }
        });
    }
    private void showNotification(Context context, String title, String message, int notifId){
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_wb_sunny_black_24dp)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        notificationManagerCompat.notify(notifId, builder.build());
    }
}
