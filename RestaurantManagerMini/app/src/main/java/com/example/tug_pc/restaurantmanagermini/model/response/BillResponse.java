package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.Bill;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillResponse {
    @SerializedName("json_bill")
    private List<Bill> listBill;
    private boolean success;
    private String message;

    public BillResponse(List<Bill> listBill, boolean success, String message) {
        this.listBill = listBill;
        this.success = success;
        this.message = message;
    }

    public List<Bill> getListBill() {
        return listBill;
    }

    public void setListBill(List<Bill> listBill) {
        this.listBill = listBill;
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
