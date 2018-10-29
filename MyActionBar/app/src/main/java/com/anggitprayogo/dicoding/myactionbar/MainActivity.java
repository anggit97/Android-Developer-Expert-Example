package com.anggitprayogo.dicoding.myactionbar;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        SearchView searchView = (SearchView)MenuItemCompat.getActionView(menu.findItem(R.id.search_view));
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: "+newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                MenuFragment menuFragment = new MenuFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
                mFragmentTransaction.add(R.id.frame_container, menuFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                return true;
            case R.id.menu2:
                Intent toActivity = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(toActivity);
                return true;
            default:
                return true;
        }
    }
}
