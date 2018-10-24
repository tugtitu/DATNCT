package com.example.tug_pc.restaurantmanagermini.model;

import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("idArea")
    private int id;
    @SerializedName("nameArea")
    private String name;

    public Area(int id, String name) {
        this.id = id;
        this.name = name;
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
}
