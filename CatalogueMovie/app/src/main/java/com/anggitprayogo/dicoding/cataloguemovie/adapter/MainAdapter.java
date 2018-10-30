package com.anggitprayogo.dicoding.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.feature.detailmovie.DetailMovieActivity;
import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<MovieResponse.Results> results;
    private Context context;


    public MainAdapter(ArrayList<MovieResponse.Results> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MovieResponse.Results model = results.get(i);

        final int position = i;

        viewHolder.getTvDate().setText(model.getReleaseDate());
        viewHolder.getTvDesc().setText(model.getOverview());
        viewHolder.getTvTitle().setText(model.getTitle());

        if (model.getBackdropPath() != null){
            Glide.with(context)
                    .load(ServiceGenerator.BASE_URL_IMAGE.concat(model.getPosterPath()))
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .into(viewHolder.getIvMovie());
        }

        viewHolder.getBtnDetail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieResponse.Results movieResponse = new MovieResponse.Results();
                movieResponse.setTitle(results.get(position).getTitle());
                movieResponse.setBackdropPath(results.get(position).getBackdropPath());
                movieResponse.setOverview(results.get(position).getOverview());
                movieResponse.setVoteAverage(results.get(position).getVoteAverage());
                movieResponse.setReleaseDate(results.get(position).getReleaseDate());

                Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResponse);

                v.getContext().startActivity(intent);
            }
        });

        viewHolder.getBtnShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, context.getString(R.string.send_text)+" "+results.get(position).getTitle());
                v.getContext().startActivity(shareIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (results != null) {
            return results.size();
        }

        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.setDebug(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MovieResponse.Results movieResponse = new MovieResponse.Results();
                    movieResponse.setTitle(results.get(getAdapterPosition()).getTitle());
                    movieResponse.setBackdropPath(results.get(getAdapterPosition()).getBackdropPath());
                    movieResponse.setOverview(results.get(getAdapterPosition()).getOverview());
                    movieResponse.setVoteAverage(results.get(getAdapterPosition()).getVoteAverage());
                    movieResponse.setReleaseDate(results.get(getAdapterPosition()).getReleaseDate());

                    Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResponse);

                    v.getContext().startActivity(intent);
                }
            });

        }

        ImageView getIvMovie() {
            return ivMovie;
        }

        TextView getTvTitle() {
            return tvTitle;
        }

        TextView getTvDesc() {
            return tvDesc;
        }

        TextView getTvDate() {
            return tvDate;
        }

        public Button getBtnDetail() {
            return btnDetail;
        }

        public Button getBtnShare() {
            return btnShare;
        }
    }
}
