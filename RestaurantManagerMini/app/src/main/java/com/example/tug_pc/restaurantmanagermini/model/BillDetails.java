package com.example.tug_pc.restaurantmanagermini.model;

import java.io.Serializable;

public class BillDetails implements Serializable {
    private int id;
    private int bill_id;
    private int food_id;
    private int quantity;
    private String note;
    private String food_name;
    private int price;
    private int promotions;
    private int status;

    public BillDetails(int id, int quantity, int food_id, int bill_id, String note, String food_name, int price, int promotions, int status) {
        this.id = id;
        this.quantity = quantity;
        this.food_id = food_id;
        this.bill_id = bill_id;
        this.note = note;
        this.food_name = food_name;
        this.price = price;
        this.promotions = promotions;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPromotions() {
        return promotions;
    }

    public void setPromotions(int promotions) {
        this.promotions = promotions;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
