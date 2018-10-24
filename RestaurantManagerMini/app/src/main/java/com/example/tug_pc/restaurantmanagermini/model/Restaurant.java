package com.example.tug_pc.restaurantmanagermini.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int id;
    private String res_name;
    private String res_address;
    private String res_describe;
    @SerializedName("phone")
    private String res_phone;

    public Restaurant(int id, String res_name, String res_address, String res_describe, String res_phone) {
        this.id = id;
        this.res_name = res_name;
        this.res_address = res_address;
        this.res_describe = res_describe;
        this.res_phone = res_phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_address() {
        return res_address;
    }

    public void setRes_address(String res_address) {
        this.res_address = res_address;
    }

    public String getRes_describe() {
        return res_describe;
    }

    public void setRes_describe(String res_describe) {
        this.res_describe = res_describe;
    }

    public String getRes_phone() {
        return res_phone;
    }

    public void setRes_phone(String res_phone) {
        this.res_phone = res_phone;
    }
}
