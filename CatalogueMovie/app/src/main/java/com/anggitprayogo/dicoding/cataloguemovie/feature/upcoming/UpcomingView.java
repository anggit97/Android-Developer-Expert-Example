package com.anggitprayogo.dicoding.cataloguemovie.feature.upcoming;

import com.anggitprayogo.dicoding.cataloguemovie.model.HomeMovieResponse;

public interface UpcomingView {

    void showLoading();

    void hideLoading();

    void showMovieResponseError(String message);

    void showMovieResponseEmpty();

    void showMovieResponseSuccess(HomeMovieResponse body);

}