package com.anggitprayogo.dicoding.mytestingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener{

    MainPresenter presenter;

    private EditText etWidth, etHeight, etLenght;
    private TextView tvResult;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWidth = findViewById(R.id.edt_width);
        etHeight = findViewById(R.id.edt_height);
        etLenght = findViewById(R.id.edt_length);
        tvResult = findViewById(R.id.tv_result);
        btnCalculate = findViewById(R.id.btn_calculate);

        presenter = new MainPresenter(this);
        btnCalculate.setOnClickListener(this);

    }

    @Override
    public void tampilkanVolume(MainModel mainModel) {
        tvResult.setText(""+mainModel.getVolume());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_calculate:
                String length = etLenght.getText().toString().trim();
                String width = etWidth.getText().toString().trim();
                String height = etHeight.getText().toString().trim();

                boolean isEmptyFields = false;

                if (TextUtils.isEmpty(length)){
                    isEmptyFields = true;
                    etLenght.setError("Field ini tidak boleh kosong");
                }

                if (TextUtils.isEmpty(width)){
                    isEmptyFields = true;
                    etWidth.setError("Field ini tidak boleh kosong");
                }

                if (TextUtils.isEmpty(height)){
                    isEmptyFields = true;
                    etHeight.setError("Field ini tidak boleh kosong");
                }

                if (!isEmptyFields){
                    double l = Double.parseDouble(length);
                    double w = Double.parseDouble(width);
                    double h = Double.parseDouble(height);

                    presenter.hitungVolume(l, w, h);
                }
                break;
        }
    }
}
