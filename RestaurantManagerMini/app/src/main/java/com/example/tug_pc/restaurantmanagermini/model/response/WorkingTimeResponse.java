package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.WorkingTime;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkingTimeResponse {
    @SerializedName("working_time")
    private List<WorkingTime> listWorkingTime;
    private boolean success;
    private String message;

    public WorkingTimeResponse(List<WorkingTime> listWorkingTime, boolean success, String message) {
        this.listWorkingTime = listWorkingTime;
        this.success = success;
        this.message = message;
    }

    public List<WorkingTime> getListWorkingTime() {
        return listWorkingTime;
    }

    public void setListWorkingTime(List<WorkingTime> listWorkingTime) {
        this.listWorkingTime = listWorkingTime;
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
