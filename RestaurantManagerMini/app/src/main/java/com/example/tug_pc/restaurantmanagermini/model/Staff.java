package com.example.tug_pc.restaurantmanagermini.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "staffs")
public class Staff implements Serializable{
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int _id;
    @SerializedName("staff_name")
    private String name;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "pass")
    @SerializedName("password")
    private String pass;
    @SerializedName("phone_number")
    private String phone;
    private String address;
    @SerializedName("kind_of_staff")
    private int kind_id;
    @SerializedName("res_id")
    private int resId;
    private int staff_root;
    private String kind_name;

    public Staff() {
    }

    @Ignore
    public Staff(int _id, String name) {
        this._id = _id;
        this.name = name;
    }

    @Ignore
    public Staff(int _id, String name, String email, String pass, String phone, String address, int kind_id, int resId, int staff_root, String kind_name) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.address = address;
        this.kind_id = kind_id;
        this.resId = resId;
        this.staff_root = staff_root;
        this.kind_name = kind_name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getKind_id() {
        return kind_id;
    }

    public void setKind_id(int kind_id) {
        this.kind_id = kind_id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getStaff_root() {
        return staff_root;
    }

    public void setStaff_root(int staff_root) {
        this.staff_root = staff_root;
    }

    public String getKind_name() {
        return kind_name;
    }

    public void setKind_name(String kind_name) {
        this.kind_name = kind_name;
    }
}
