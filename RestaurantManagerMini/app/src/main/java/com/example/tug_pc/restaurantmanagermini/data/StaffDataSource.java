package com.example.tug_pc.restaurantmanagermini.data;

import com.example.tug_pc.restaurantmanagermini.model.Staff;

import java.util.List;

import io.reactivex.Flowable;

public interface StaffDataSource {
    Flowable<Staff> getStaffById(int staffId);

    Flowable<List<Staff>> getStaffByEmail(String email);

    Flowable<List<Staff>> loadingStaff();

    void insertStaff(Staff... staffs);

    void deleteStaff(Staff staff);

    void deleteAllStaff();

    void updateStaff(Staff... staffs);
}
