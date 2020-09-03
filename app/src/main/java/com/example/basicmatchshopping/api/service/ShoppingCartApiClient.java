package com.example.basicmatchshopping.api.service;

import com.example.basicmatchshopping.api.response.ShoppingCartResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ShoppingCartApiClient {

    @GET("shoppingcart/getactivebyuserid/{id}")
    Call<ShoppingCartResponse> getActiveShoppingCartByUserId(@Path("id") String id, @Header("Authorization") String authorization);
}
