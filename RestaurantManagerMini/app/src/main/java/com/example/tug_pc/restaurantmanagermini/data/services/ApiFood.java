package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.FoodResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiFood {
    @GET("getFood.php")
    Call<FoodResponse> getDataFood(@Query("id") int idKFood);

    @GET("updateFood.php")
    Call<ResultResponse> updateFood(
            @Query("is") int is,
            @Query("id") int idFood,
            @Query("food_name") String food_name,
            @Query("promotions") long promotions,
            @Query("price") long price,
            @Query("kind_food") int idKindFood);
}