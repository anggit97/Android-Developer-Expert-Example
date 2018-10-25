package com.anggitprayogo.dicoding.latihanintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MoveDataWithObjectActivity extends AppCompatActivity {

    public static final String DataObjectValue = "DataObject";
    TextView tvObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_data_with_object);

        tvObject = findViewById(R.id.tv_object_received);

        Person mPerson = getIntent().getParcelableExtra(DataObjectValue);

        tvObject.setText("Nama : "+mPerson.getName()+", Usia : "+mPerson.getAge());

    }
}
