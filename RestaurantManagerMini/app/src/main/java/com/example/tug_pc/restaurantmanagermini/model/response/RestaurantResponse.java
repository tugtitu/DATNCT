package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.Restaurant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantResponse {
    @SerializedName("success")
    private boolean isSuccess;
    private String message;
    @SerializedName("restaurant")
    private List<Restaurant> listRestaurant;

    public RestaurantResponse(boolean isSuccess, String message, List<Restaurant> listRestaurant) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.listRestaurant = listRestaurant;
    }

    public boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Restaurant> getListRestaurant() {
        return listRestaurant;
    }

    public void setListRestaurant(List<Restaurant> listRestaurant) {
        this.listRestaurant = listRestaurant;
    }
}
