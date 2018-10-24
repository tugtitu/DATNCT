package com.example.tug_pc.restaurantmanagermini.data.services;

import com.example.tug_pc.restaurantmanagermini.model.response.BillResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiBill {
    @GET("getBillByTable.php")
    Call<BillResponse> getOrderBar(@Query("id") int idT);

    @GET("updateDataBillDetails.php")
    Call<Result> updateDataBillDetails(
            @Query("idBill") int idBill,
            @Query("idTable") int idTable,
            @Query("idStaff") int idStaff,
            @Query("idFood") int idFood,
            @Query("idInfo") int idBillDetails,
            @Query("isPlus") int isPlus,
            @Query("note") String note
    );

    @GET("updateBill.php")
    Call<Result> updateBill(
            @Query("idBill") int idBill,
            @Query("idTable") int idTable,
            @Query("is") int is
    );

    @GET("billPayment.php")
    Call<BillResponse> billPayment(
            @Query("idBill") int idBill,
            @Query("idStaff") int idStaff,
            @Query("idTable") int idTable,
            @Query("current_total") long current_total,
            @Query("guest_money") long guest_money,
            @Query("money_back") long money_back,
            @Query("discount") long discount,
            @Query("surcharge") long surcharge,
            @Query("ship") long ship,
            @Query("customer") String cus,
            @Query("note") String note
    );

    @GET("getBillByDate.php")
    Call<BillResponse> getBillByDate(@Query("from_date") String from_date, @Query("to_date") String to_date);

    @GET("getBillByFood.php")
    Call<BillResponse> getBillByFood(@Query("idFood") int id);

    @GET("processingFinished.php")
    Call<Result> processingFinished(@Query("idD") int idD, @Query("idB") int idB, @Query("status") int status);
}