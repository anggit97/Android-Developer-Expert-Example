package com.anggitprayogo.dicoding.cataloguemovie.feature.detailmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @BindView(R.id.iv_movie)
    ImageView ivMovie;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.tv_date)
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        MovieResponse.Results extras = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (extras != null) {
            tvTitle.setText(extras.getTitle());
            tvRate.setText("" + extras.getVoteAverage());
            tvOverview.setText(extras.getOverview());
            tvDate.setText(extras.getReleaseDate());


            Glide.with(this)
                    .load(ServiceGenerator.BASE_URL_IMAGE.concat(extras.getBackdropPath()))
                    .thumbnail(Glide.with(this).load(R.drawable.loading))
                    .into(ivMovie);
        }

    }
}
