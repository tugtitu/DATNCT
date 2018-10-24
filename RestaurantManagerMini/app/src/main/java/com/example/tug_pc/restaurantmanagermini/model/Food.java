package com.example.tug_pc.restaurantmanagermini.model;

public class Food {
    private int id;
    private String food_name;
    private int kind_food;
    private int quantity;
    private int price;
    private int promotions;
    private String kind_food_name;

    public Food(int id, String food_name, String kind_food_name) {
        this.id = id;
        this.food_name = food_name;
        this.kind_food_name = kind_food_name;
    }

    public Food(int id, String food_name, int kind_food, int quantity, int price, int promotions, String kind_food_name) {
        this.id = id;
        this.food_name = food_name;
        this.kind_food = kind_food;
        this.quantity = quantity;
        this.price = price;
        this.promotions = promotions;
        this.kind_food_name = kind_food_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getKind_food() {
        return kind_food;
    }

    public void setKind_food(int kind_food) {
        this.kind_food = kind_food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getKind_food_name() {
        return kind_food_name;
    }

    public void setKind_food_name(String kind_food_name) {
        this.kind_food_name = kind_food_name;
    }
}
