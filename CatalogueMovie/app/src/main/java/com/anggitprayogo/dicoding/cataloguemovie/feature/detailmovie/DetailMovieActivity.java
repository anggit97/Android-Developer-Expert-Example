package com.anggitprayogo.dicoding.cataloguemovie.feature.detailmovie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.db.DatabaseContract;
import com.anggitprayogo.dicoding.cataloguemovie.db.MovieHelper;
import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    private MovieHelper movieHelper;
    private Menu menu;
    private MenuItem menuItem;
    private String id;
    private boolean isStar;
    private MovieResponse.Results extras;

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (extras != null) {
            tvTitle.setText(extras.getTitle());
            tvRate.setText("" + extras.getVoteAverage());
            tvOverview.setText(extras.getOverview());
            tvDate.setText(extras.getReleaseDate());

            getSupportActionBar().setTitle(extras.getTitle());

            if (extras.getPosterPath() != null){
                Glide.with(this)
                        .load(ServiceGenerator.BASE_URL_IMAGE.concat(extras.getPosterPath()))
                        .thumbnail(Glide.with(this).load(R.drawable.loading))
                        .apply(new RequestOptions().error(R.drawable.reload))
                        .into(ivMovie);

            }else{
                Glide.with(this)
                        .load(R.drawable.reload)
                        .thumbnail(Glide.with(this).load(R.drawable.loading))
                        .apply(new RequestOptions().error(R.drawable.reload))
                        .into(ivMovie);
            }


            id = String.valueOf(extras.getId());
        }

        movieHelper = new MovieHelper(this);

        movieHelper.open();

    }

    private void checkState(){
        if (isStar){
            menu.findItem(R.id.action_star).setIcon(getResources().getDrawable(R.drawable.ic_star_24dp));
        }else{
            menu.findItem(R.id.action_star).setIcon(getResources().getDrawable(R.drawable.ic_star_border_24dp));
        }
        invalidateOptionsMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Cursor cursor = null;

        cursor = movieHelper.queryByIdProvider(id);

        if (cursor.getCount() > 0){
            isStar = true;
        }else{
            isStar = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_activity, menu);
        this.menu = menu;

        checkState();

        return true;
    }

    private boolean insertToDb(){
        boolean status = true;

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.COLUMN_FAVOURITE.ID, extras.getId());
        contentValues.put(DatabaseContract.COLUMN_FAVOURITE.TITLE, extras.getTitle());
        contentValues.put(DatabaseContract.COLUMN_FAVOURITE.RELEASE_DATE, extras.getReleaseDate());
        contentValues.put(DatabaseContract.COLUMN_FAVOURITE.BACKDROP_PATH, extras.getBackdropPath());
        contentValues.put(DatabaseContract.COLUMN_FAVOURITE.POPULARITY, extras.getPopularity());
        contentValues.put(DatabaseContract.COLUMN_FAVOURITE.POSTER_PATH, extras.getPosterPath());
        contentValues.put(DatabaseContract.COLUMN_FAVOURITE.VOTE_AVARAGE, extras.getVoteAverage());
        contentValues.put(DatabaseContract.COLUMN_FAVOURITE.VOTE_COUNT, extras.getVoteCount());
        contentValues.put(DatabaseContract.COLUMN_FAVOURITE.OVERVIEW, extras.getOverview());

        long id = movieHelper.insert(contentValues);

        if (id <= 0){
            Toast.makeText(this, R.string.failed_add_to_favourite, Toast.LENGTH_SHORT).show();
            status = false;
        }

        return status;
    }

    private boolean deleteFromDb(String id){
        boolean status = true;

        int result = movieHelper.delete(id);

        if (result <= 0){
            Toast.makeText(this, R.string.failed_remove_from_favourite, Toast.LENGTH_SHORT).show();
            status = false;
        }

        return status;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_star:

                if (isStar){
                    if (deleteFromDb(id)){
                        Toast.makeText(this, R.string.remove_from_favourite, Toast.LENGTH_SHORT).show();
                        isStar = false;
                    }
                }else{
                    if (insertToDb()){
                        Toast.makeText(this, R.string.add_to_favourite, Toast.LENGTH_SHORT).show();
                        isStar = true;
                    }
                }

                checkState();

                return true;
            case R.id.action_share:
                Intent shareIntent = new Intent();
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.send_text));
                startActivity(shareIntent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }
}
