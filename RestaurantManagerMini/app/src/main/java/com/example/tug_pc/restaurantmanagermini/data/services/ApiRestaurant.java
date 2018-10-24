package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.RestaurantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRestaurant {
    @GET("getRestaurant.php")
    Call<RestaurantResponse> getRestaurant( @Query("idRes") int idRestaurant );
}