package com.example.basicmatchshopping.api.service;

import com.example.basicmatchshopping.api.request.LoginRequest;
import com.example.basicmatchshopping.api.request.UserRequest;
import com.example.basicmatchshopping.api.response.TokenResponse;
import com.example.basicmatchshopping.api.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiClient {

    @POST("auth/login")
    Call<TokenResponse> login(@Body LoginRequest loginRequest);

    @GET("user/get/{username}")
    Call<UserResponse> get(@Path("username") String username, @Header("Authorization") String authorization);

    @POST("user/create")
    Call<UserResponse> create(@Body UserRequest userRequest);
}
