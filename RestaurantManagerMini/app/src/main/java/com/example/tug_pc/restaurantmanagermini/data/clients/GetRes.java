package com.example.tug_pc.restaurantmanagermini.data.clients;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.example.tug_pc.restaurantmanagermini.data.services.ApiRestaurant;
import com.example.tug_pc.restaurantmanagermini.model.response.RestaurantResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRes {
    public GetRes() { }

    public void getRestaurant(ActionBar supportActionBar, int idRes) {
        ApiRestaurant apiRestaurant = ApiClient.getClient().create(ApiRestaurant.class);
        Call<RestaurantResponse> call = apiRestaurant.getRestaurant(idRes);
        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantResponse> call, @NonNull Response<RestaurantResponse> response) {
                RestaurantResponse res = response.body();
                assert res != null;
                if (res.getSuccess()) {
                    supportActionBar.setTitle(res.getListRestaurant().get(0).getRes_name());
                } else {
                    supportActionBar.setTitle("asaasa");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantResponse> call, @NonNull Throwable t) {
                Log.e("TAG - res", t.getMessage()) ;
            }
        });
    }
}
