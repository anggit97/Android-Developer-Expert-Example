package com.anggitprayogo.dicoding.cataloguemovie.feature.mainactivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.adapter.MainAdapter;
import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;
import com.anggitprayogo.dicoding.cataloguemovie.util.HideKeyboard;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    private final String TAG = getClass().getSimpleName();

    private ArrayList<MovieResponse.Results> results = new ArrayList<>();
    private MainPresenter presenter;
    private MainAdapter adapter;
    private String keyword;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(null);

        ServiceGenerator serviceGenerator = new ServiceGenerator();

        presenter = new MainPresenter(this, serviceGenerator, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMovie.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(results, this);
        rvMovie.setAdapter(adapter);

        presenter.getMovie("a");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMovie(keyword);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search_view));
        searchView.setQueryHint(getResources().getString(R.string.hint_search));
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            Handler handler = new Handler();

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                keyword = query;
                presenter.getMovie(query);
                HideKeyboard.hideKeyboard(MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: " + newText);

                final String query = newText;

                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        keyword = query;
                        presenter.getMovie(query);
                    }
                }, 300);


                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_view:
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMovieResponseError(String message) {
        Log.d(TAG, "showMovieResponseError: " + message);
    }

    @Override
    public void showMovieResponseEmpty() {
        Toast.makeText(this, "Kosong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovieResponseSuccess(MovieResponse body) {
        results.clear();
        results.addAll(body.getResults());
        adapter.notifyDataSetChanged();
    }
}
