package com.anggitprayogo.dicoding.favouriteapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.anggitprayogo.dicoding.favouriteapp.R;
import com.anggitprayogo.dicoding.favouriteapp.adapter.FavouriteAppAdapter;
import com.anggitprayogo.dicoding.favouriteapp.db.DatabaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.anggitprayogo.dicoding.favouriteapp.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
         AdapterView.OnItemClickListener {

    private final String TAG = getClass().getSimpleName();

    private FavouriteAppAdapter adapter;

    private static final int LOAD_MOVIE_ID = 110;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.rv_movie)
    ListView rvMovie;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.favourite_movie);
        getSupportActionBar().setTitle(R.string.favourite_movie);

        adapter = new FavouriteAppAdapter(this, null, true);
        rvMovie.setAdapter(adapter);
        rvMovie.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_MOVIE_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_MOVIE_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null){
            Toast.makeText(this, "Kosong", Toast.LENGTH_SHORT).show();
        }
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_MOVIE_ID);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor)adapter.getItem(position);

        String movieId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.ID));
        Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
        intent.setData(Uri.parse(CONTENT_URI+"/"+movieId));
        startActivity(intent);
    }
}
