package com.anggitprayogo.dicoding.cataloguemovie.feature.mainactivity;

import android.content.Context;
import android.util.Log;

import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.EndpointClient;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private final String TAG = getClass().getSimpleName();
    private MainView view;
    private ServiceGenerator serviceGenerator;
    private Context context;

    public MainPresenter(MainView view, ServiceGenerator serviceGenerator, Context context) {
        this.view = view;
        this.serviceGenerator = serviceGenerator;
        this.context = context;
    }

    public void getMovie(String query) {
        view.showLoading();
        Call<MovieResponse> call = serviceGenerator.createService(EndpointClient.class, context).searchMovie(ServiceGenerator.API_KEY,
                ServiceGenerator.LANG, query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                view.hideLoading();
                if (response.isSuccessful()){
                    if (response.body().getResults().size() == 0){
                        view.showMovieResponseEmpty();
                    }else{
                        view.showMovieResponseSuccess(response.body());
                    }
                }else{
                    view.showMovieResponseError(context.getString(R.string.error_message_failed_to_fetch));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                view.hideLoading();
                view.showMovieResponseError(t.getMessage());
            }
        });
    }
}