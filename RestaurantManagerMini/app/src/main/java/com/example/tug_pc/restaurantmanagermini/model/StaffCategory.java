package com.example.tug_pc.restaurantmanagermini.model;

public class StaffCategory {
    private int id;
    private String kind_name;

    public StaffCategory(int id, String kind_name) {
        this.id = id;
        this.kind_name = kind_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind_name() {
        return kind_name;
    }

    public void setKind_name(String kind_name) {
        this.kind_name = kind_name;
    }
}
