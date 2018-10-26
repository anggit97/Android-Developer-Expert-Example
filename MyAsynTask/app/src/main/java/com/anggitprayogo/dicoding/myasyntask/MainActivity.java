package com.anggitprayogo.dicoding.myasyntask;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);

        DemoAsyntask demoAsyntask = new DemoAsyntask();
        demoAsyntask.execute("Statusnya misalnya");

    }

    @SuppressLint("StaticFieldLeak")
    class DemoAsyntask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            tvStatus.setText("OnPreExecute");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: "+strings[0]);

            try {
                Thread.sleep(5000);
            }catch (Exception e){
                Log.d(TAG, "doInBackground: "+e.getMessage());
            }

            return strings[0];
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: ");
            tvStatus.setText(s);
        }
    }
}
