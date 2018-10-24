package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.Result;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.TableResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiTable {
    @GET("getTableByArea.php")
    Call<TableResponse> getDataTable(@Query("id") int idA);

    @GET("getTableManageByArea.php")
    Call<TableResponse> getDataManageTable(@Query("id") int idA);

    @GET("getTableById.php")
    Call<TableResponse> getTableById(@Query("id") int idT);

    @GET("switchTable.php")
    Call<Result> handleTransfer(@Query("id1") int idT1, @Query("id2") int idT2);

    @GET("mergeTable.php")
    Call<Result> handleMerge(@Query("id1") int idT1, @Query("id2") int idT2);

    @GET("updateTable.php")
    Call<ResultResponse> updateTable(@Query("is") int is, @Query("id") int id, @Query("name") String name, @Query("idArea") int idArea);

    @GET("updateStatusTable.php")
    Call<Result> updateStatusTable(@Query("idTable") int idTable, @Query("status") int status,  @Query("time") String time_booking, @Query("phone") String phone);
}