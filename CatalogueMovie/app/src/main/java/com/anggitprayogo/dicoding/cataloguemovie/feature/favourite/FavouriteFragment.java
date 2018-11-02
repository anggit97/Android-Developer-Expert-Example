package com.anggitprayogo.dicoding.cataloguemovie.feature.favourite;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.adapter.HomeAdapter;
import com.anggitprayogo.dicoding.cataloguemovie.db.DatabaseContract;
import com.anggitprayogo.dicoding.cataloguemovie.db.MovieHelper;
import com.anggitprayogo.dicoding.cataloguemovie.model.HomeMovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavouriteFragment extends Fragment {

    private HomeAdapter homeAdapter;
    private ArrayList<HomeMovieResponse.Results> results = new ArrayList<>();
    private MovieHelper movieHelper;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, view);

        movieHelper = new MovieHelper(getActivity());
        movieHelper.open();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMovie.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(results, getActivity());
        rvMovie.setAdapter(homeAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadFavouriteMovie().execute();
    }

    private void showResults(){
        rvMovie.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void hideResults(){
        rvMovie.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    class LoadFavouriteMovie extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hideResults();
            results.clear();;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Cursor cursor = movieHelper.query();

            cursor.moveToFirst();

            if (cursor.getCount() > 0){
                do {
                    HomeMovieResponse.Results result = new HomeMovieResponse.Results();
                    result.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.ID))));
                    result.setVoteCount(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.VOTE_COUNT))));
                    result.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.VOTE_AVARAGE))));
                    result.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.TITLE)));
                    result.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.POSTER_PATH)));
                    result.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.BACKDROP_PATH)));
                    result.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.RELEASE_DATE)));
                    result.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.OVERVIEW)));
                    result.setPopularity(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.POPULARITY))));
                    results.add(result);
                    cursor.moveToNext();

                }while (!cursor.isAfterLast());
            }else{
                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showResults();

            if (results.size() == 0){
                Toast.makeText(getActivity(), R.string.message_favourite_empty, Toast.LENGTH_SHORT).show();
            }

            homeAdapter = new HomeAdapter(results, getActivity());
            rvMovie.setAdapter(homeAdapter);
            homeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
