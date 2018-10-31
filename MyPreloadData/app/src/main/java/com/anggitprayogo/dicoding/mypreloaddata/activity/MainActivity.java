package com.anggitprayogo.dicoding.mypreloaddata.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.anggitprayogo.dicoding.mypreloaddata.R;
import com.anggitprayogo.dicoding.mypreloaddata.db.AppPrefrence;
import com.anggitprayogo.dicoding.mypreloaddata.db.MahasiswaHelper;
import com.anggitprayogo.dicoding.mypreloaddata.entity.Mahasiswa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    class LoadData extends AsyncTask<Void, Integer, Void>{

        final String TAG = getClass().getSimpleName();
        MahasiswaHelper mahasiswaHelper;
        AppPrefrence appPrefrence;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            mahasiswaHelper = new MahasiswaHelper(MainActivity.this);
            appPrefrence = new AppPrefrence(MainActivity.this);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean isFirstRun = appPrefrence.getFirstRun();

            if (isFirstRun){

                ArrayList<Mahasiswa> mahasiswas = preLoadData();

                mahasiswaHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / mahasiswas.size();

                mahasiswaHelper.begintransaction();

                try {
                    for (Mahasiswa model : mahasiswas) {
                        mahasiswaHelper.insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    // Jika semua proses telah di set success maka akan di commit ke database
                    mahasiswaHelper.setTransactionSuccess();
                } catch (Exception e) {
                    // Jika gagal maka do nothing
                    Log.e(TAG, "doInBackground: Exception");
                }

                mahasiswaHelper.endTransactionSuccess();

                mahasiswaHelper.close();

                appPrefrence.setFirstRun(false);

                publishProgress((int) maxProgress);
            }else{
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxProgress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(MainActivity.this, MahasiswaActivity.class);
            startActivity(i);
            finish();
        }
    }

    private ArrayList<Mahasiswa> preLoadData() {
        ArrayList<Mahasiswa> mahasiswas = new ArrayList<>();
        String line = null;
        BufferedReader bufferedReader;

        InputStream inputStream = getResources().openRawResource(R.raw.data_mahasiswa);

        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try{
            int count = 0;

            do {
                line = bufferedReader.readLine();

                String[] splitstr = line.split("\t");

                Mahasiswa mahasiswaModel;

                mahasiswaModel = new Mahasiswa(splitstr[1], splitstr[0]);
                mahasiswas.add(mahasiswaModel);
                count++;
            } while (line != null);

            count++;

        }catch (Exception e){
            e.printStackTrace();
        }


        return mahasiswas;
    }
}
