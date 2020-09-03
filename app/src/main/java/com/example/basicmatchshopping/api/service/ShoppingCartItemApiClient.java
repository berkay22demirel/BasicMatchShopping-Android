package com.example.basicmatchshopping.api.service;

import com.example.basicmatchshopping.api.request.ShoppingCartItemRequest;
import com.example.basicmatchshopping.api.response.ResultResponse;
import com.example.basicmatchshopping.api.response.ShoppingCartItemResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ShoppingCartItemApiClient {

    @POST("shoppingcartitem/add")
    Call<ShoppingCartItemResponse> create(@Body ShoppingCartItemRequest shoppingCartItemRequest, @Header("Authorization") String authorization);

    @PUT("shoppingcartitem/update")
    Call<ResultResponse> update(@Body ShoppingCartItemRequest shoppingCartItemRequest, @Header("Authorization") String authorization);
}
