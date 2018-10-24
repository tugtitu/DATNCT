package com.example.tug_pc.restaurantmanagermini.model;

import android.graphics.drawable.Drawable;

public class GeneralSetting {
    private int id;
    private Drawable icon;
    private String name;

    public GeneralSetting(int id, Drawable icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
