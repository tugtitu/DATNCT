package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.KindFood;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KindFoodResponse {
    @SerializedName("kind_food")
    private List<KindFood> listKindFood;
    private int status;
    private String message;

    public KindFoodResponse(List<KindFood> listKindFood, int status, String message) {
        this.listKindFood = listKindFood;
        this.status = status;
        this.message = message;
    }

    public List<KindFood> getListKindFood() {
        return listKindFood;
    }

    public void setListKindFood(List<KindFood> listKindFood) {
        this.listKindFood = listKindFood;
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
