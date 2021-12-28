package com.example.mealplanner.service;

import com.example.mealplanner.model.Category;
import com.example.mealplanner.model.GenericIngredient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @POST("/signup")
    Call<ResponseBody> signup(@Query("username") String username,
                              @Query("password") String password);

    @POST("/login")
    Call<ResponseBody> login(@Query("username") String username,
                              @Query("password") String password);

    @GET("/getCategories")
    Call<List<Category>> getCategories();

    @GET("/getAllGenericIngredients")
    Call<List<GenericIngredient>> getGenericIngredients();
}
