package com.example.tug_pc.restaurantmanagermini.data;

import com.example.tug_pc.restaurantmanagermini.model.Staff;

import java.util.List;

import io.reactivex.Flowable;

public class StaffRepository implements StaffDataSource {
    private static StaffRepository sInstance;

    public static StaffRepository getInstance(StaffDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new StaffRepository(localDataSource);
        }
        return sInstance;
    }

    private StaffDataSource mLocalDataSource;

    public StaffRepository(StaffDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    @Override
    public Flowable<Staff> getStaffById(int staffId) {
        return mLocalDataSource.getStaffById(staffId);
    }

    @Override
    public Flowable<List<Staff>> getStaffByEmail(String email) {
        return mLocalDataSource.getStaffByEmail(email);
    }

    @Override
    public Flowable<List<Staff>> loadingStaff() {
        return mLocalDataSource.loadingStaff();
    }

    @Override
    public void insertStaff(Staff... staffs) {
        mLocalDataSource.insertStaff(staffs);
    }

    @Override
    public void deleteStaff(Staff staff) {
        mLocalDataSource.deleteStaff(staff);
    }

    @Override
    public void deleteAllStaff() {
        mLocalDataSource.deleteAllStaff();
    }

    @Override
    public void updateStaff(Staff... staffs) {
        mLocalDataSource.updateStaff(staffs);
    }
}
