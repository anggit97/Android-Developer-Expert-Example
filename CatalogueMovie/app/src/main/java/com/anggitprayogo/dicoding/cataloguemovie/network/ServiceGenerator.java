package com.anggitprayogo.dicoding.cataloguemovie.network;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w185/";
    public static final String API_KEY = "00fadd6af89412de4c1a3ecd7fe631f6";
    public static final String LANG = "en-US";

    private static String BASE_URL = "https://api.themoviedb.org/";

    private static long cacheSize = (5*1024*1024);

    private static Retrofit retrofit;

    private static Cache cache;

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static final OkHttpClient.Builder client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS);

    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    public static <S> S createService(Class<S> service, Context context){

        cache = new Cache(context.getCacheDir(), cacheSize);

        if (!client.interceptors().contains(logging)){
            client.addInterceptor(logging);
            builder.client(client.cache(cache).build());
            retrofit = builder.build();
        }

        return retrofit.create(service);
    }

}
