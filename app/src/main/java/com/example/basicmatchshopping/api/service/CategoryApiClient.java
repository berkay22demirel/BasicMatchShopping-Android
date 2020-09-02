package com.example.basicmatchshopping.api.service;

import com.example.basicmatchshopping.api.response.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApiClient {

    @GET("category/getall")
    Call<List<CategoryResponse>> getAll();
}
