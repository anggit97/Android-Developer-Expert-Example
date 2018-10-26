package com.anggitprayogo.dicoding.myservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnStartService, btnStartIntentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btn_start_service);
        btnStartIntentService = findViewById(R.id.btn_start_intent_service);

        btnStartService.setOnClickListener(this);
        btnStartIntentService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_service:
                Intent startService = new Intent(MainActivity.this, OriginService.class);
                startService(startService);
                break;
            case R.id.btn_start_intent_service:
                Intent startIntentService = new Intent(MainActivity.this, OriginIntentService.class);
                startIntentService.putExtra(OriginIntentService.EXTRA_DURATION, 5000);
                startService(startIntentService);
                break;
        }
    }
}
