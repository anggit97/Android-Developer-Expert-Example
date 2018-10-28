package com.anggitprayogo.dicoding.myweatherloader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<WheaterItems>>{

    ListView listView;
    WeatherAdapter weatherAdapter;
    EditText editKota;
    Button btnCari;

    private static final String EXTRAS_CITY = "EXTRAS_CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherAdapter = new WeatherAdapter(this);
        weatherAdapter.notifyDataSetChanged();
        listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(weatherAdapter);

        editKota = (EditText)findViewById(R.id.edit_kota);
        btnCari = (Button)findViewById(R.id.btn_kota);

        btnCari.setOnClickListener(myListener);

        String kota = editKota.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CITY,kota);

        getLoaderManager().initLoader(0, bundle, MainActivity.this);
    }

    //Fungsi ini yang akan menjalankan proses myasynctaskloader
    @Override
    public Loader<ArrayList<WheaterItems>> onCreateLoader(int id, Bundle args) {

        String cities = "";
        if (args != null) {
            cities = args.getString(EXTRAS_CITY);
        }

        return new MyAsyntaskLoader(this, cities);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<WheaterItems>> loader, ArrayList<WheaterItems> wheaterItems) {
        weatherAdapter.setItems(wheaterItems);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<WheaterItems>> loader) {
        weatherAdapter.setItems(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String kota = editKota.getText().toString();

            if (TextUtils.isEmpty(kota))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_CITY,kota);
            getLoaderManager().restartLoader(0,bundle,MainActivity.this);
        }
    };
}
