package com.example.tug_pc.restaurantmanagermini.model.response;

import com.google.gson.annotations.SerializedName;

public class ResultResponse {
    @SerializedName("success")
    private int result;
    private String message;

    public ResultResponse(int result, String message) {
        this.result = result;
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
