package com.example.basicmatchshopping.api.service;

import com.example.basicmatchshopping.api.response.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApiClient {

    @GET("product/getallbycategoryid/{id}")
    Call<List<ProductResponse>> getAllByCategoryId(@Path("id") String id);
}
