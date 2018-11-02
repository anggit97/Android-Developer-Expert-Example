package com.anggitprayogo.dicoding.favouriteapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anggitprayogo.dicoding.favouriteapp.R;
import com.anggitprayogo.dicoding.favouriteapp.db.DatabaseContract;
import com.anggitprayogo.dicoding.favouriteapp.network.ServiceGenerator;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.anggitprayogo.dicoding.favouriteapp.db.DatabaseContract.getColumnString;

public class FavouriteAppAdapter extends CursorAdapter {

    @BindView(R.id.iv_movie)
    ImageView ivMovie;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.btn_detail)
    Button btnDetail;
    @BindView(R.id.btn_share)
    Button btnShare;

    public FavouriteAppAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ButterKnife.bind(this, view);

        if (cursor != null) {

            tvDate.setText(getColumnString(cursor, DatabaseContract.COLUMN_FAVOURITE.RELEASE_DATE));
            tvDesc.setText(getColumnString(cursor, DatabaseContract.COLUMN_FAVOURITE.OVERVIEW));
            tvTitle.setText(getColumnString(cursor, DatabaseContract.COLUMN_FAVOURITE.TITLE));

            if (getColumnString(cursor,DatabaseContract.COLUMN_FAVOURITE.POSTER_PATH) != null){
                Glide.with(context)
                        .load(ServiceGenerator.BASE_URL_IMAGE.concat(getColumnString(cursor,DatabaseContract.COLUMN_FAVOURITE.POSTER_PATH)))
                        .thumbnail(Glide.with(context).load(R.drawable.loading))
                        .into(ivMovie);
            }else{
                Glide.with(context)
                        .load(R.drawable.reload)
                        .thumbnail(Glide.with(context).load(R.drawable.loading))
                        .into(ivMovie);
            }

        }
    }
}
