package com.example.tug_pc.restaurantmanagermini.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Bill implements Serializable{
    @SerializedName("idBill")
    private int id;
    @SerializedName("idTable")
    private int table_id;
    @SerializedName("idStaff")
    private int staff_id;
    private long total_price;
    private String time_order;
    private String customer;
    private long guest_money;
    private long money_back;
    private int discount;
    private long surcharge;
    private int ship;
    private String staff_name;
    private String bill_note;
    private int status;
    private String bill_date;
    @SerializedName("bill_details")
    private List<BillDetails> listBillDetails;

    public Bill(int id, int table_id, int staff_id, long total_price, String time_order, String customer, long guest_money, long money_back, int discount, long surcharge, int ship, String staff_name, String bill_note, int status, String bill_date, List<BillDetails> listBillDetails) {
        this.id = id;
        this.table_id = table_id;
        this.staff_id = staff_id;
        this.total_price = total_price;
        this.time_order = time_order;
        this.customer = customer;
        this.guest_money = guest_money;
        this.money_back = money_back;
        this.discount = discount;
        this.surcharge = surcharge;
        this.ship = ship;
        this.staff_name = staff_name;
        this.bill_note = bill_note;
        this.status = status;
        this.bill_date = bill_date;
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

    public long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(long total_price) {
        this.total_price = total_price;
    }

    public String getTime_order() {
        return time_order;
    }

    public void setTime_order(String time_order) {
        this.time_order = time_order;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public long getGuest_money() {
        return guest_money;
    }

    public void setGuest_money(long guest_money) {
        this.guest_money = guest_money;
    }

    public long getMoney_back() {
        return money_back;
    }

    public void setMoney_back(long money_back) {
        this.money_back = money_back;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public long getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(long surcharge) {
        this.surcharge = surcharge;
    }

    public int getShip() {
        return ship;
    }

    public void setShip(int ship) {
        this.ship = ship;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getBill_note() {
        return bill_note;
    }

    public void setBill_note(String bill_note) {
        this.bill_note = bill_note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public List<BillDetails> getListBillDetails() {
        return listBillDetails;
    }

    public void setListBillDetails(List<BillDetails> listBillDetails) {
        this.listBillDetails = listBillDetails;
    }
}
