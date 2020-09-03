package com.example.basicmatchshopping.api.service;

import com.example.basicmatchshopping.api.request.OrderRequest;
import com.example.basicmatchshopping.api.response.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApiClient {

    @POST("order/buy")
    Call<OrderResponse> buy(@Body OrderRequest orderRequest, @Header("Authorization") String authorization);

    @GET("order/getallbyuserid/{id}")
    Call<List<OrderResponse>> getAllByUserId(@Path("id") String id, @Header("Authorization") String authorization);
}
