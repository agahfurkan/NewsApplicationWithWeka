package com.agah.furkan.newsapplicationwithweka.data.web;

import java.util.Map;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface INewsRetrofit {
    @GET("/v2/top-headlines?pageSize=100&country=us&apiKey=" + GlobalCons.NEWSAPI_API_KEY)
    Call<ModelForNewsRequest> getTopNews(@Query("category") String category);

    @GET("/v2/top-headlines?pageSize=100&country=us&apiKey=" + GlobalCons.NEWSAPI_API_KEY)
    Call<ModelForNewsRequest> getAllTopNews(@Query("category") String category);

    @GET("/v2/everything/")
    Call<ModelForNewsRequest> getNews(@QueryMap Map<String, String> params);

}
