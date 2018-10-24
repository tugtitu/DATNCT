package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.Staff;

import java.util.List;

public class StaffResponse {
    private List<Staff> listStaff;
    private boolean success;
    private String message;

    public StaffResponse(List<Staff> listStaff, boolean success, String message) {
        this.listStaff = listStaff;
        this.success = success;
        this.message = message;
    }

    public List<Staff> getListStaff() {
        return listStaff;
    }

    public void setListStaff(List<Staff> listStaff) {
        this.listStaff = listStaff;
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
