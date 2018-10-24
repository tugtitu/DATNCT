package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.Area;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {
    @SerializedName("json_area")
    private List<Area> listArea;
    private boolean success;
    private String message;

    public AreaResponse(List<Area> listArea, boolean success, String message) {
        this.listArea = listArea;
        this.success = success;
        this.message = message;
    }

    public List<Area> getListArea() {
        return listArea;
    }

    public void setListArea(List<Area> listArea) {
        this.listArea = listArea;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
