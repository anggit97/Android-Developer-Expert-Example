package com.anggitprayogo.dicoding.myalarmmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvOneTimeDate, tvOneTimeTime, tvRepeatingTime;
    private EditText etOneTimeMessage, etRepeatingMessage;
    private Button btnOneTimeDate, btnOneTimeTime, btnOneTime, btnRepeating, btnRepeatingTime, btnRepeatingAlarmCancel;

    private Calendar calOneTimeDate, calOneTimeTime, calRepeatTimeTime;

    private AlarmReciever alarmReceiver;
    private AlarmPrefrence alarmPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOneTimeDate = findViewById(R.id.tv_one_time_alarm_date);
        tvOneTimeTime = findViewById(R.id.tv_one_time_alarm_time);
        etOneTimeMessage = findViewById(R.id.edt_one_time_alarm_message);
        tvRepeatingTime = findViewById(R.id.tv_repeating_alarm_time);
        etRepeatingMessage = findViewById(R.id.tv_repeating_alarm_message);

        btnOneTimeDate = findViewById(R.id.btn_one_time_alarm_date);
        btnOneTimeTime = findViewById(R.id.btn_one_time_alarm_time);
        btnOneTime = findViewById(R.id.btn_set_one_time_alarm);
        btnRepeatingTime = findViewById(R.id.btn_repeating_time_alarm_time);
        btnRepeating = findViewById(R.id.btn_repeating_time_alarm);
        btnRepeatingAlarmCancel = findViewById(R.id.btn_cancel_repeating_alarm);

        btnOneTimeDate.setOnClickListener(this);
        btnOneTimeTime.setOnClickListener(this);
        btnOneTime.setOnClickListener(this);
        btnRepeatingTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);
        btnRepeatingAlarmCancel.setOnClickListener(this);

        calOneTimeDate = Calendar.getInstance();
        calOneTimeTime = Calendar.getInstance();
        calRepeatTimeTime = Calendar.getInstance();

        alarmPreference = new AlarmPrefrence(this);
        alarmReceiver = new AlarmReciever();
        if (!TextUtils.isEmpty(alarmPreference.getKEY_ONE_TIME_DATE())){
            setOneTimeText();
        }

        if (!TextUtils.isEmpty(alarmPreference.getKEY_REPEATING_TIME())){
            setRepeatingText();
        }
    }

    private void setRepeatingText() {
        tvRepeatingTime.setText(alarmPreference.getKEY_REPEATING_TIME());
        etRepeatingMessage.setText(alarmPreference.getKEY_REPEATING_MESSAGE());
    }

    private void setOneTimeText() {
        tvOneTimeDate.setText(alarmPreference.getKEY_ONE_TIME_DATE());
        tvOneTimeTime.setText(alarmPreference.getKEY_ONE_TIME_TIME());
        etOneTimeMessage.setText(alarmPreference.getKEY_ONE_TIME_MESSAGE());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_one_time_alarm_date:

                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calOneTimeDate.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        tvOneTimeDate.setText(simpleDateFormat.format(calOneTimeDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show();

                break;
            case R.id.btn_one_time_alarm_time:

                Calendar calTime = Calendar.getInstance();

                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calOneTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calOneTimeTime.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        tvOneTimeTime.setText(simpleDateFormat.format(calOneTimeTime.getTime()));

                    }
                }, calTime.get(Calendar.HOUR_OF_DAY), calTime.get(Calendar.MINUTE),true).show();

                break;
            case R.id.btn_set_one_time_alarm:
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String oneTimeDate = dateFormat.format(calOneTimeDate.getTime());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String oneTimeTime = timeFormat.format(calOneTimeTime.getTime());
                String oneTimeMessage = etOneTimeMessage.getText().toString();
                alarmPreference.setKEY_ONE_TIME_DATE(oneTimeDate);
                alarmPreference.setKEY_ONE_TIME_MESSAGE(oneTimeMessage);
                alarmPreference.setKEY_ONE_TIME_TIME(oneTimeTime);
                alarmReceiver.setOneTimeAlarm(this, AlarmReciever.TYPE_ONE_TIME,
                        alarmPreference.getKEY_ONE_TIME_DATE(),
                        alarmPreference.getKEY_ONE_TIME_TIME(),
                        alarmPreference.getKEY_ONE_TIME_MESSAGE());
                break;
            case R.id.btn_repeating_time_alarm_time:
                Toast.makeText(this, "this", Toast.LENGTH_SHORT).show();
                Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calRepeatTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calRepeatTimeTime.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        tvRepeatingTime.setText(simpleDateFormat.format(calRepeatTimeTime.getTime()));
                    }
                }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE),true).show();
                break;
            case R.id.btn_repeating_time_alarm:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                String repeatingTime = simpleDateFormat.format(calRepeatTimeTime.getTime());
                alarmPreference.setKEY_REPEATING_TIME(repeatingTime);
                alarmPreference.setKEY_REPEATING_MESSAGE(etRepeatingMessage.getText().toString());
                alarmReceiver.setRepeatingAlarm(this, AlarmReciever.TYPE_REPEATING, alarmPreference.getKEY_REPEATING_TIME(), alarmPreference.getKEY_REPEATING_MESSAGE());
                break;
        }
    }
}
