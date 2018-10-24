package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.KindFoodResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiKindFood {
    @GET("getKindFood.php")
    Call<KindFoodResponse> getAllDataKindFood();

    @GET("updateKindFood.php")
    Call<ResultResponse> updateKindFood(
            @Query("is") int is,
            @Query("id") int id,
            @Query("name") String name);
}