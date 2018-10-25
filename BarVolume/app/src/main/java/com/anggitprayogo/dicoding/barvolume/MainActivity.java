package com.anggitprayogo.dicoding.barvolume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String STATE_HASIL = "state_hasil";

    Button btnHitung;
    EditText etPanjang, etLebar, etTinggi;
    TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPanjang = findViewById(R.id.tv_panjang);
        etLebar = findViewById(R.id.tv_lebar);
        etTinggi = findViewById(R.id.tv_tinggi);
        btnHitung = findViewById(R.id.btn_hitung);
        tvHasil = findViewById(R.id.tv_hasil);

        btnHitung.setOnClickListener(this);

        if (savedInstanceState != null){
            String hasil = savedInstanceState.getString(STATE_HASIL);
            tvHasil.setText(hasil);
        }
    }

    private int hitungVolume(int panjang, int lebar, int tinggi) {
        return panjang * lebar * tinggi;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_HASIL, tvHasil.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_hitung:
                tvHasil.setText(
                        String.valueOf(hitungVolume(
                                Integer.parseInt(etPanjang.getText().toString()),
                                Integer.parseInt(etLebar.getText().toString()),
                                Integer.parseInt(etTinggi.getText().toString())
                        ))
                );
                break;
        }
    }
}
