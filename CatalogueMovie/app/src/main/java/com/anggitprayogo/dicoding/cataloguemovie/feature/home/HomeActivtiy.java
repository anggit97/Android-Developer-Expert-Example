package com.anggitprayogo.dicoding.cataloguemovie.feature.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.feature.favourite.FavouriteFragment;
import com.anggitprayogo.dicoding.cataloguemovie.feature.homefragment.HomeFragment;
import com.anggitprayogo.dicoding.cataloguemovie.feature.mainactivity.MainActivity;
import com.anggitprayogo.dicoding.cataloguemovie.feature.nowplaying.NowPlayingFragment;
import com.anggitprayogo.dicoding.cataloguemovie.feature.settings.SettingsActivity;
import com.anggitprayogo.dicoding.cataloguemovie.feature.upcoming.UpcomingFragment;
import com.anggitprayogo.dicoding.cataloguemovie.prefrence.PrefrenceUtil;
import com.anggitprayogo.dicoding.cataloguemovie.reciever.AlarmReciever;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivtiy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = getClass().getSimpleName();
    private AlarmReciever alarmReciever;
    private PrefrenceUtil prefrenceUtil;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activtiy);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragment(new HomeFragment());

        alarmReciever = new AlarmReciever();
        prefrenceUtil = new PrefrenceUtil(this);

        if (!prefrenceUtil.isDailyRemainderExist()){
            setupDialyRemainder();
        }

        if (!prefrenceUtil.isReleaseRemainderExists()){
            setupReleaseRemainder();
        }

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activtiy, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                Intent toChangeLanguage = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(toChangeLanguage);
                return true;
            case R.id.action_search:
                Intent toMainActivity = new Intent(HomeActivtiy.this, MainActivity.class);
                startActivity(toMainActivity);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment, TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        switch (id){
            case R.id.nav_home:
                fragment = new HomeFragment();
                setActionBarTitle(getString(R.string.home));
                break;
            case R.id.nav_search:
                Intent toMainActivity = new Intent(HomeActivtiy.this, MainActivity.class);
                startActivity(toMainActivity);
                break;
            case R.id.nav_star:
                fragment = new FavouriteFragment();
                setActionBarTitle(getString(R.string.favourite));
                break;
            case R.id.nav_settings:
                Intent toSettingActivity = new Intent(HomeActivtiy.this, SettingsActivity.class);
                startActivity(toSettingActivity);
                break;
        }

        if (fragment != null){
            showFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
