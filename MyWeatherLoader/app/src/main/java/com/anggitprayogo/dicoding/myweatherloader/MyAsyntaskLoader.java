package com.anggitprayogo.dicoding.myweatherloader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyntaskLoader extends AsyncTaskLoader<ArrayList<WheaterItems>> {

    final String APP_ID = "827ccc94b9d80b6937cae986bc675e5e";
    final String CITY = "Jakarta";


    private ArrayList<WheaterItems> dataItems = new ArrayList<>();
    private boolean mhasResult = false;

    private String mKumpulanKota;

    public MyAsyntaskLoader(@NonNull Context context, String kumpulanKota) {
        super(context);

        onContentChanged();
        mKumpulanKota = kumpulanKota;
    }

    @Override
    protected void onStartLoading() {
        Log.d("Content Changed","1");
        if (takeContentChanged())
            forceLoad();
        else if (mhasResult)
            deliverResult(dataItems);
    }

    @Override
    public void deliverResult(@Nullable ArrayList<WheaterItems> data) {
        dataItems = data;
        mhasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mhasResult) {
            onReleaseResources(dataItems);
            dataItems = null;
            mhasResult = false;
        }
    }

    protected void onReleaseResources(ArrayList<WheaterItems> data) {
        //nothing to do.
    }



    @Nullable
    @Override
    public ArrayList<WheaterItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<WheaterItems> items = new ArrayList<>();
        String url = "http://api.openweathermap.org/data/2.5/group?id=" +
                mKumpulanKota+ "&units=metric&appid=" + APP_ID;

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject weather = list.getJSONObject(i);
                        WheaterItems weatherItems = new WheaterItems(weather);
                        items.add(weatherItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return items;
    }
}
