package com.anggitprayogo.dicoding.cataloguemovie.feature.mainactivity;

import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;

public interface MainView {

    void tampilkanMovie();

    void showLoading();

    void hideLoading();

    void showMovieResponseError(String message);

    void showMovieResponseEmpty();

    void showMovieResponseSuccess(MovieResponse body);
}
