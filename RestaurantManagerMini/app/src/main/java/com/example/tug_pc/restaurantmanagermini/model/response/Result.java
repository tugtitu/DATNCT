package com.example.tug_pc.restaurantmanagermini.model.response;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("success")
    private boolean isSuccess;
    private String message;

    public Result(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
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
}
