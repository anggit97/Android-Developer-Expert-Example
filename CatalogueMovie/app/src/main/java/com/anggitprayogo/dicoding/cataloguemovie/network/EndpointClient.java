package com.anggitprayogo.dicoding.cataloguemovie.network;

import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;
import com.anggitprayogo.dicoding.cataloguemovie.model.HomeMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface EndpointClient {

    @Headers("Accept: application/json")
    @GET("3/search/movie")
    Call<MovieResponse> searchMovie(@Query("api_key") String apiKey,
                                    @Query("language") String language,
                                    @Query("query") String query);

    @Headers("Accept: application/json")
    @GET("3/movie/now_playing")
    Call<HomeMovieResponse> nowPlaying(@Query("api_key") String apiKey,
                                       @Query("language") String language);

    @Headers("Accept: application/json")
    @GET("3/movie/upcoming")
    Call<HomeMovieResponse> upcomingMovie(@Query("api_key") String apiKey,
                                      @Query("language") String language);

}
