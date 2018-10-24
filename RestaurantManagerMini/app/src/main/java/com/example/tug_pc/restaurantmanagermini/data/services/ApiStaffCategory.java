package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.StaffCategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiStaffCategory {
    @GET("getStaffCategory.php")
    Call<StaffCategoryResponse> getStaffCategory();
}