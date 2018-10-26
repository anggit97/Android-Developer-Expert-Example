package com.anggitprayogo.dicoding.smslistenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmsRecieverActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = getClass().getSimpleName();

    public static String EXTRAS_SMS_NO = "extras_sms_no";
    public static String EXTRAS_SMS_MESSAGE = "extras_sms_message";

    private TextView tvMessage, tvNo;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_reciever);

        tvMessage = findViewById(R.id.tv_message);
        tvNo = findViewById(R.id.tv_no);
        btnClose = findViewById(R.id.btn_close);

        btnClose.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String phoneNumber = extras.getString(EXTRAS_SMS_NO);
            String message = extras.getString(EXTRAS_SMS_MESSAGE);

            tvNo.setText("Dari : "+phoneNumber);
            tvMessage.setText(message);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close:
                break;
        }
    }
}
