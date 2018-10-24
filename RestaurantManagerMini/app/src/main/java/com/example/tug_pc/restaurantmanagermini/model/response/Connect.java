package com.example.tug_pc.restaurantmanagermini.model.response;

public class Connect {
    private boolean success;

    public Connect(boolean success) {
        this.success = success;
    }


    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
