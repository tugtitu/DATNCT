package com.example.tug_pc.restaurantmanagermini.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Table implements Serializable {
    private int id;
    @SerializedName("table_name")
    private String name;
    @SerializedName("area_id")
    private int area;
    private String time_booking;
    @SerializedName("customer_phone")
    private String phone_number;
    private int status;
    @SerializedName("time_order")
    private String time;
    private String area_name;

    public Table(int id, String name, int area, String time_booking, String phone_number, int status, String time, String area_name) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.time_booking = time_booking;
        this.phone_number = phone_number;
        this.status = status;
        this.time = time;
        this.area_name = area_name;
    }

    public Table(int id, String name, String area_name) {
        this.id = id;
        this.name = name;
        this.area_name = area_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getTime_booking() {
        return time_booking;
    }

    public void setTime_booking(String time_booking) {
        this.time_booking = time_booking;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", time_booking='" + time_booking + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", status=" + status +
                ", time='" + time + '\'' +
                ", area_name='" + area_name + '\'' +
                '}';
    }
}
