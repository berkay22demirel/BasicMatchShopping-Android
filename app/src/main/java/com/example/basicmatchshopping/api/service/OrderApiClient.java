package com.example.basicmatchshopping.api.service;

import com.example.basicmatchshopping.api.response.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderApiClient {

    @GET("order/getallbyuserid/{id}")
    Call<List<OrderResponse>> getAllByUserId(@Path("id") String id);
}
