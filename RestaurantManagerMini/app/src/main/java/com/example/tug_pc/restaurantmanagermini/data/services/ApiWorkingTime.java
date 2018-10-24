package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.ResultWorking;
import com.example.tug_pc.restaurantmanagermini.model.response.WorkingTimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWorkingTime {
    @GET("getWorkingTime.php")
    Call<WorkingTimeResponse> getWorkingTime(@Query("id") int id);

    @GET("updateWorkingTime.php")
    Call<ResultWorking> updateWorkingTime(
            @Query("is") int is,
            @Query("id") int id,
            @Query("idStaff") int idStaff,
            @Query("name") String name,
            @Query("weekdays") String weekdays,
            @Query("from_hour") String from_hour,
            @Query("come_hour") String come_hour);
}