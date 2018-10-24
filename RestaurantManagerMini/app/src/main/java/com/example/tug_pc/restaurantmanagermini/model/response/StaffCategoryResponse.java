package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.StaffCategory;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffCategoryResponse {
    @SerializedName("staff_category")
    private List<StaffCategory> listStaffCategory;
    private int success;
    private String message;

    public StaffCategoryResponse(List<StaffCategory> listStaffCategory, int success, String message) {
        this.listStaffCategory = listStaffCategory;
        this.success = success;
        this.message = message;
    }

    public List<StaffCategory> getListStaffCategory() {
        return listStaffCategory;
    }

    public void setListStaffCategory(List<StaffCategory> listStaffCategory) {
        this.listStaffCategory = listStaffCategory;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
