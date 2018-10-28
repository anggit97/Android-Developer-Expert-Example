package com.anggitprayogo.dicoding.cataloguemovie.network;

import com.anggitprayogo.dicoding.cataloguemovie.model.MovieResponse;

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
}
