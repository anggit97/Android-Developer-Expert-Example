package com.anggitprayogo.dicoding.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public void setResults(ArrayList<MovieResponse.Results> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void addResult(MovieResponse.Results result) {
        results.add(result);
        notifyDataSetChanged();
    }

    public void clearResults() {
        results.clear();
        notifyDataSetChanged();
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

        viewHolder.getTvDate().setText(model.getReleaseDate());
        viewHolder.getTvDesc().setText(model.getOverview());
        viewHolder.getTvTitle().setText(model.getTitle());

        if (model.getBackdropPath() != null){
            Glide.with(context)
                    .load(ServiceGenerator.BASE_URL_IMAGE.concat(model.getPosterPath()))
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .into(viewHolder.getIvMovie());
        }

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

        public ImageView getIvMovie() {
            return ivMovie;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvDesc() {
            return tvDesc;
        }

        public TextView getTvDate() {
            return tvDate;
        }
    }
}
