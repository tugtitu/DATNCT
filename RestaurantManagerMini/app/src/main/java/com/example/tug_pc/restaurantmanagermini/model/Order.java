package com.example.tug_pc.restaurantmanagermini.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{
    @SerializedName("idOrder")
    private int id;
    @SerializedName("idTable")
    private int table_id;
    @SerializedName("idStaff")
    private int staff_id;
    private int total_price;
    @SerializedName("date_time")
    private String date;
    private int status;
    @SerializedName("order_info")
    private List<BillDetails> listBillDetails;
    public String table_name;
    public String area;

    public Order(int id, int table_id, int staff_id, int total_price, String date, int status, List<BillDetails> listBillDetails) {
        this.id = id;
        this.table_id = table_id;
        this.staff_id = staff_id;
        this.total_price = total_price;
        this.date = date;
        this.status = status;
        this.listBillDetails = listBillDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BillDetails> getListBillDetails() {
        return listBillDetails;
    }

    public void setListBillDetails(List<BillDetails> listBillDetails) {
        this.listBillDetails = listBillDetails;
    }
}
