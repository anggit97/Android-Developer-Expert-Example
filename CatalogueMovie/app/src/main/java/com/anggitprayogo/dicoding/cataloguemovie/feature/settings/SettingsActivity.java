package com.anggitprayogo.dicoding.cataloguemovie.feature.settings;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.prefrence.PrefrenceUtil;
import com.anggitprayogo.dicoding.cataloguemovie.reciever.AlarmReciever;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private PrefrenceUtil prefrenceUtil;
    private AlarmReciever alarmReciever;
    private boolean isDailyRemainderChecked = true, isReleaseRemainderChecked = true;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_change_language)
    TextView tvChangeLanguage;
    @BindView(R.id.switch_daily_remainder)
    Switch switchDailyRemainder;
    @BindView(R.id.switch_release_remainder)
    Switch switchReleaseRemainder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);
        prefrenceUtil = new PrefrenceUtil(this);
        alarmReciever = new AlarmReciever();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.label_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupDialyRemainder();
        setupReleaseRemainder();

        if (!prefrenceUtil.getDailyRemainder()){
            switchDailyRemainder.setChecked(false);
            switchDailyRemainder.setText(R.string.label_off);
        }

        if (!prefrenceUtil.getReleaseRemainder()){
            switchReleaseRemainder.setChecked(false);
            switchReleaseRemainder.setText(R.string.label_off);
        }

        switchDailyRemainder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    switchDailyRemainder.setText(R.string.label_on);
                    isDailyRemainderChecked = true;
                    prefrenceUtil.setDailyRemainder(isDailyRemainderChecked);
                    startDailyRemainder();
                }else{
                    switchDailyRemainder.setText(R.string.label_off);
                    isDailyRemainderChecked = false;
                    prefrenceUtil.setDailyRemainder(isDailyRemainderChecked);
                    cancelDailyRemainder();
                }
            }
        });

        switchReleaseRemainder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    switchReleaseRemainder.setText(R.string.label_on);
                    isReleaseRemainderChecked = true;
                    prefrenceUtil.setReleaseRemainder(isReleaseRemainderChecked);
                    startReleaseRemainder();
                }else{
                    switchReleaseRemainder.setText(R.string.label_off);
                    isReleaseRemainderChecked = false;
                    prefrenceUtil.setReleaseRemainder(isReleaseRemainderChecked);
                    cancelReleaseRemainder();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    @OnClick(R.id.tv_change_language)
    public void OnChangeLanguage(){
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(intent);
    }

    private void setupDialyRemainder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        prefrenceUtil.setDailyDate(simpleDateFormat.format(calendar.getTime()));
    }

    private void setupReleaseRemainder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        prefrenceUtil.setReleaseDate(simpleDateFormat.format(calendar.getTime()));
    }

    private void cancelDailyRemainder(){
        alarmReciever.cancelDailyRemainder(this);
    }

    private void startDailyRemainder(){
        alarmReciever.startDailyRemainder(this, AlarmReciever.DAILY_REMAINDER_TYPE, prefrenceUtil.getDailyDate(), "Jangan lupa kunjungi kami hari ini ya..");
    }

    private void cancelReleaseRemainder(){
        alarmReciever.cancelReleaseRemainder(this);
    }

    private void startReleaseRemainder(){
        alarmReciever.startReleaseRemainder(this, AlarmReciever.RELEASE_REMAINDER_TYPE, prefrenceUtil.getReleaseDate(), "Jangan lupa senyum hari ini:)");
    }
}