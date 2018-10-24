package com.example.tug_pc.restaurantmanagermini.model;

import android.graphics.drawable.Drawable;

public class Main {
    private int id;
    private String name;
    private Drawable img;

    public Main(int id, String name, Drawable img) {
        this.id = id;
        this.name = name;
        this.img = img;
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

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }
}
