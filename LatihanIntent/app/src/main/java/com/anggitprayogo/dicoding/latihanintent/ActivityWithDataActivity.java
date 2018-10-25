package com.anggitprayogo.dicoding.latihanintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityWithDataActivity extends AppCompatActivity {

    public static final String NAME = "name";
    public static final String AGE = "age";

    TextView tvName, tvAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_data);

        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nama = extras.getString(NAME, "");
            int age = extras.getInt(AGE);

            tvName.setText("Name : "+nama);
            tvAge.setText("Age : "+age);
        }
    }
}
