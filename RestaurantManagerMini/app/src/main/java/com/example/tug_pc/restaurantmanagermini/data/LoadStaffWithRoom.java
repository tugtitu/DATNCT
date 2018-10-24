package com.example.tug_pc.restaurantmanagermini.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.data.local.StaffDatabase;
import com.example.tug_pc.restaurantmanagermini.data.local.StaffLocalDataSource;
import com.example.tug_pc.restaurantmanagermini.model.Staff;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoadStaffWithRoom {
    @SuppressLint("StaticFieldLeak")
    private static LoadStaffWithRoom sInstance;

    public static LoadStaffWithRoom getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LoadStaffWithRoom(context);
        }
        return sInstance;
    }
    private static Staff staff;
    private Context context;

    public LoadStaffWithRoom(Context context) {
        this.context = context;
        getData();
    }

    public void getData() {

        StaffDatabase staffDatabase = StaffDatabase.getInstance(context);
        StaffRepository mStaffRepository = StaffRepository.getInstance(StaffLocalDataSource.getInstance(staffDatabase.staffDAO()));

        CompositeDisposable mCompositeDisposable = new CompositeDisposable();

        Disposable disposable = mStaffRepository.loadingStaff()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(staffs -> {
                    staff = staffs.get(0);
                }, throwable -> onGetAllStaffFailure(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void onGetAllStaffFailure(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Log.e("TAG - message", message);
    }

    public Staff getStaff() {
        return staff;
    }
}
