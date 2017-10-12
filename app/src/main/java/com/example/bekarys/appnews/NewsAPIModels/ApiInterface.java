package com.example.bekarys.appnews.NewsAPIModels;

import com.example.bekarys.appnews.NewsModels.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bekarys on 09.10.17.
 */

public interface ApiInterface {


    @GET("api/jsonBlob/9e44dca2-ad19-11e7-894a-5dc7a0a132b5")
    Call<List<News>> getNewsAPIList();

    @PUT("api/jsonBlob/9e44dca2-ad19-11e7-894a-5dc7a0a132b5")
    Call<List<News>> createNewsAPIBlob();

    @DELETE("/api/jsonBlob/9e44dca2-ad19-11e7-894a-5dc7a0a132b5")
    Call deletePost();

}
