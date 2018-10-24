package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.BillDetails;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderInfoResponse {
    @SerializedName("order_info")
    private List<BillDetails> listBillDetails;
    private int status;
    private String message;

    public OrderInfoResponse(List<BillDetails> listOrder, int status, String message) {
        this.listBillDetails = listOrder;
        this.status = status;
        this.message = message;
    }

    public List<BillDetails> getListBillDetails() {
        return listBillDetails;
    }

    public void setListBillDetails(List<BillDetails> listOrder) {
        this.listBillDetails = listOrder;
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
