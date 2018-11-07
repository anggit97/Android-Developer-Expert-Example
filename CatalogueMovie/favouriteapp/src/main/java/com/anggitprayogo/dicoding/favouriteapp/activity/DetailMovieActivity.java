package com.anggitprayogo.dicoding.favouriteapp.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.anggitprayogo.dicoding.favouriteapp.R;
import com.anggitprayogo.dicoding.favouriteapp.db.DatabaseContract;
import com.anggitprayogo.dicoding.favouriteapp.entitiy.HomeMovieResponse;
import com.anggitprayogo.dicoding.favouriteapp.network.ServiceGenerator;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.anggitprayogo.dicoding.favouriteapp.db.DatabaseContract.getColumnString;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.iv_movie)
    ImageView ivMovie;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_overview)
    TextView tvOverview;

    private HomeMovieResponse.Results results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        Uri uri = getIntent().getData();

        if (uri != null){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()) results = new HomeMovieResponse.Results(cursor);
                cursor.close();
            }

            tvDate.setText(results.getReleaseDate());
            tvTitle.setText(results.getTitle());
            tvOverview.setText(results.getOverview());
            tvRate.setText(""+results.getVoteAverage());

            if (results.getPosterPath() != null){
                Glide.with(this)
                        .load(ServiceGenerator.BASE_URL_IMAGE.concat(results.getPosterPath()))
                        .thumbnail(Glide.with(this).load(R.drawable.loading))
                        .into(ivMovie);
            }
        }
    }
}
