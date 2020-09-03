package com.example.basicmatchshopping.api;

import com.example.basicmatchshopping.api.service.CategoryApiClient;
import com.example.basicmatchshopping.api.service.ProductApiClient;
import com.example.basicmatchshopping.api.service.UserApiClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static CategoryApiClient getCategoryApiClient() {
        CategoryApiClient categoryApiClient = getRetrofit().create(CategoryApiClient.class);
        return categoryApiClient;
    }

    public static ProductApiClient getProductApiClient() {
        ProductApiClient productApiClient = getRetrofit().create(ProductApiClient.class);
        return productApiClient;
    }

    public static UserApiClient getUserApiClient() {
        UserApiClient userApiClient = getRetrofit().create(UserApiClient.class);
        return userApiClient;
    }
}
