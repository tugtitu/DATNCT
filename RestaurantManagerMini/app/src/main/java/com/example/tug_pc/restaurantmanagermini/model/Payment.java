package com.example.tug_pc.restaurantmanagermini.model;

public class Payment {
    private long into_money = 0;
    private long currentTotal = 0;
    private long cash = 0;
    private long change = 0;
    private long discount = 0;
    private long surcharge = 0;
    private long ship = 0;

    public Payment(long into_money, long currentTotal, long cash, long change, long discount, long surcharge, long ship) {
        this.into_money = into_money;
        this.currentTotal = currentTotal;
        this.cash = cash;
        this.change = change;
        this.discount = discount;
        this.surcharge = surcharge;
        this.ship = ship;
    }

    public Payment() {
    }

    public long getInto_money() {
        return into_money;
    }

    public void setInto_money(long into_money) {
        this.into_money = into_money;
    }

    public long getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(long currentTotal) {
        this.currentTotal = currentTotal;
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public long getChange() {
        return change;
    }

    public void setChange(long change) {
        this.change = change;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public long getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(long surcharge) {
        this.surcharge = surcharge;
    }

    public long getShip() {
        return ship;
    }

    public void setShip(long ship) {
        this.ship = ship;
    }
}
