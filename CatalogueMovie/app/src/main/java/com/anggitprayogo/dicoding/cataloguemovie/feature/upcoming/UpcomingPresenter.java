package com.anggitprayogo.dicoding.cataloguemovie.feature.upcoming;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.anggitprayogo.dicoding.cataloguemovie.BuildConfig;
import com.anggitprayogo.dicoding.cataloguemovie.R;
import com.anggitprayogo.dicoding.cataloguemovie.model.HomeMovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.network.EndpointClient;
import com.anggitprayogo.dicoding.cataloguemovie.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingPresenter {

    private final String TAG = getClass().getSimpleName();
    private UpcomingView view;
    private ServiceGenerator serviceGenerator;
    private Context context;

    public UpcomingPresenter(UpcomingView view, ServiceGenerator serviceGenerator, Context context) {
        this.view = view;
        this.serviceGenerator = serviceGenerator;
        this.context = context;
    }

    public void upcoming() {
        view.showLoading();
        Call<HomeMovieResponse> call = serviceGenerator.createService(EndpointClient.class, context)
                .upcomingMovie(BuildConfig.OPEN_WEATHER_MAP_API_KEY, ServiceGenerator.LANG);
        call.enqueue(new Callback<HomeMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<HomeMovieResponse> call, @NonNull Response<HomeMovieResponse> response) {
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
            public void onFailure(Call<HomeMovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                view.hideLoading();
                view.showMovieResponseError(t.getMessage());
            }
        });
    }

}