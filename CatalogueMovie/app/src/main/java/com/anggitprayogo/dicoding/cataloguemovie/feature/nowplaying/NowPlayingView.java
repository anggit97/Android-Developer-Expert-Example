package com.anggitprayogo.dicoding.cataloguemovie.feature.nowplaying;

import com.anggitprayogo.dicoding.cataloguemovie.model.HomeMovieResponse;

public interface NowPlayingView {

    void showLoading();

    void hideLoading();

    void showMovieResponseError(String message);

    void showMovieResponseEmpty();

    void showMovieResponseSuccess(HomeMovieResponse body);
}
