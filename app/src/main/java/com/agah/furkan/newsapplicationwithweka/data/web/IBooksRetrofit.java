package com.agah.furkan.newsapplicationwithweka.data.web;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface IBooksRetrofit {

    @GET("/books/v1/volumes?")
    Call<ModelForBooksRequest> getBooks(@QueryMap Map<String, String> params);
}
