package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.Result;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.StaffResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiStaff {
    @GET("getStaff.php")
    Call<StaffResponse> getStaffChildByRoot(@Query("id") int id);

    @GET("updateInformationStaff.php")
    Call<Result> updateInformationStaff(
            @Query("is") int is,
            @Query("id") int id,
            @Query("staff_name") String staff_name,
            @Query("email") String email,
            @Query("pass") String pass,
            @Query("phone") String phone,
            @Query("address") String address,
            @Query("kind_of_staff") int idCategory,
            @Query("staff_root") int staff_root);

    @GET("deleteStaff.php")
    Call<ResultResponse> deleteStaff(@Query("id") int id);

    @GET("login.php")
    Call<StaffResponse> login(@Query("email") String email, @Query("pass") String pass);
}