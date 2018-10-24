package com.example.tug_pc.restaurantmanagermini.model.response;

public class ResultWorking {
    private  int idWorkingTime;
    private boolean success;
    private String message;

    public ResultWorking(int idWorkingTime, boolean success, String message) {
        this.idWorkingTime = idWorkingTime;
        this.success = success;
        this.message = message;
    }

    public int getIdWorkingTime() {
        return idWorkingTime;
    }

    public void setIdWorkingTime(int idWorkingTime) {
        this.idWorkingTime = idWorkingTime;
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
