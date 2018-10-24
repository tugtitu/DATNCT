package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.Connect;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiConnect {
    @GET("haveConnection.php")
    Call<Connect> haveConnection();
}