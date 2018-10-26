package com.anggitprayogo.dicoding.myflexiblefragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anggitprayogo.dicoding.myflexiblefragment.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFragment();

    }

    private void setupFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        fragment = new HomeFragment();
        mFragmentTransaction.add(R.id.frame_cotainer, fragment, TAG);
        mFragmentTransaction.commit();
    }
}
