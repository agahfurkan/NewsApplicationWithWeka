package com.agah.furkan.newsapplicationwithweka.data.web;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RestApi {
    private static Retrofit newsRetrofit = null;
    private static Retrofit booksRetrofit = null;

    public static Retrofit getNewsClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                int resCode = Character.getNumericValue(String.valueOf(response.code()).charAt(0));
                if (resCode == 1) {
                    Timber.tag("timber").d(ErrorMessage.INFORMATIONAL);
                } else if (resCode == 2) {
                    Timber.tag("timber").d(ErrorMessage.SUCCESS);
                } else if (resCode == 3) {
                    Timber.tag("timber").d(ErrorMessage.REDIRECTION);
                } else if (resCode == 4) {
                    Timber.tag("timber").d(ErrorMessage.CLIENT);
                } else if (resCode == 5) {
                    Timber.tag("timber").d(ErrorMessage.SERVER);
                }
                return response;
            }
        })
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        newsRetrofit = new Retrofit.Builder()
                .baseUrl(GlobalCons.NEWS_API_BASE_URL)//URL
                .addConverterFactory(GsonConverterFactory.create())//Converter
                .client(client)
                .build();
        return newsRetrofit;
    }

    public static Retrofit getBooksClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                int resCode = Character.getNumericValue(String.valueOf(response.code()).charAt(0));
                if (resCode == 1) {
                    Timber.tag("timber").d(ErrorMessage.INFORMATIONAL);
                } else if (resCode == 2) {
                    Timber.tag("timber").d(ErrorMessage.SUCCESS);
                } else if (resCode == 3) {
                    Timber.tag("timber").d(ErrorMessage.REDIRECTION);
                } else if (resCode == 4) {
                    Timber.tag("timber").d(ErrorMessage.CLIENT);
                } else if (resCode == 5) {
                    Timber.tag("timber").d(ErrorMessage.SERVER);
                }
                return response;
            }
        })
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        booksRetrofit = new Retrofit.Builder()
                .baseUrl(GlobalCons.BOOKS_API_BASE_URL)//URL
                .addConverterFactory(GsonConverterFactory.create())//Converter
                .client(client)
                .build();
        return booksRetrofit;
    }
}
