package com.example.tug_pc.restaurantmanagermini.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.tug_pc.restaurantmanagermini.model.Staff;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface StaffDAO {
    @Query("SELECT * FROM staffs WHERE id = :staffId")
    Flowable<Staff> getStaffById(int staffId);

    @Query("SELECT * FROM staffs WHERE email LIKE :email")
    Flowable<List<Staff>> getStaffByEmail(String email);

    @Query("SELECT * FROM staffs")
    Flowable<List<Staff>> getALlStaff();

    @Insert
    void insertStaff(Staff... staffs);

    @Delete
    void deleteStaff(Staff staff);

    @Query("DELETE FROM staffs")
    void deleteAllStaff();

    @Update
    void updateStaff(Staff... staffs);
}
