package com.example.mealplanner.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @POST("/signup")
    Call<ResponseBody> signup(@Query("username") String username,
                              @Query("password") String password);

    @POST("/login")
    Call<ResponseBody> login(@Query("username") String username,
                              @Query("password") String password);
}
