package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.AreaResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiArea {
    @GET("getArea.php")
    Call<AreaResponse> displayArea();

    @GET("updateArea.php")
    Call<ResultResponse> updateArea(@Query("is") int is, @Query("id") int id, @Query("name") String name);
}