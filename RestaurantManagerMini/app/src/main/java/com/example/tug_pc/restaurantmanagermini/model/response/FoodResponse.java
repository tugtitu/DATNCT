package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.Food;
import com.example.tug_pc.restaurantmanagermini.model.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodResponse {
    @SerializedName("food")
    private List<Food> listFood;
    private int status;
    private String message;

    public FoodResponse(List<Food> listFood, int status, String message) {
        this.listFood = listFood;
        this.status = status;
        this.message = message;
    }

    public List<Food> getListFood() {
        return listFood;
    }

    public void setListFood(List<Food> listFood) {
        this.listFood = listFood;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
