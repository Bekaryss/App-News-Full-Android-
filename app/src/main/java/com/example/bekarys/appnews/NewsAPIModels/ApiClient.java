package com.example.bekarys.appnews.NewsAPIModels;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bekarys on 09.10.17.
 */

public class ApiClient {

    public static final String BASE_URL = "https://jsonblob.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient.Builder okHTTPClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHTTPClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
