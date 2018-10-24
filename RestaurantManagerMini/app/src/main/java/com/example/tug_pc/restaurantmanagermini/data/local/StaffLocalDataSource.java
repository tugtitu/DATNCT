package com.example.tug_pc.restaurantmanagermini.data.local;

import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.data.StaffDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class StaffLocalDataSource implements StaffDataSource {
    private static StaffLocalDataSource sInstance;

    public static StaffLocalDataSource getInstance(StaffDAO staffDAO) {
        if (sInstance == null) {
            sInstance = new StaffLocalDataSource(staffDAO);
        }
        return sInstance;
    }

    private StaffDAO mStaffDAO;

    public StaffLocalDataSource(StaffDAO staffDAO) {
        mStaffDAO = staffDAO;
    }

    @Override
    public Flowable<Staff> getStaffById(int staffId) {
        return mStaffDAO.getStaffById(staffId);
    }

    @Override
    public Flowable<List<Staff>> getStaffByEmail(String email) {
        return mStaffDAO.getStaffByEmail(email);
    }

    @Override
    public Flowable<List<Staff>> loadingStaff() {
        return mStaffDAO.getALlStaff();
    }

    @Override
    public void insertStaff(Staff... staffs) {
        mStaffDAO.insertStaff(staffs);
    }

    @Override
    public void deleteStaff(Staff staff) {
        mStaffDAO.deleteStaff(staff);
    }

    @Override
    public void deleteAllStaff() {
        mStaffDAO.deleteAllStaff();
    }

    @Override
    public void updateStaff(Staff... staffs) {
        mStaffDAO.updateStaff(staffs);
    }
}