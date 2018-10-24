package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.data.StaffRepository;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.local.StaffDatabase;
import com.example.tug_pc.restaurantmanagermini.data.local.StaffLocalDataSource;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiConnect;
import com.example.tug_pc.restaurantmanagermini.model.response.Connect;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenLogInActivity extends AppCompatActivity {
    private Intent mIntent;
    private StaffRepository mStaffRepository;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_log_in);

        StaffDatabase staffDatabase = StaffDatabase.getInstance(this);
        mStaffRepository =
                StaffRepository.getInstance(StaffLocalDataSource.getInstance(staffDatabase.staffDAO()));

        mCompositeDisposable = new CompositeDisposable();

        checkConnection();
    }

    private void checkConnection() {

        ApiConnect apiConnect = ApiClient.getClient().create(ApiConnect.class);
        Call<Connect> call = apiConnect.haveConnection();
        call.enqueue(new Callback<Connect>() {
            @Override
            public void onResponse(@NonNull Call<Connect> call, @NonNull Response<Connect> response) {
                Connect connect = response.body();
                assert connect != null;
                if (connect.getSuccess()) {
                    SharedPreferences pre = getSharedPreferences ("my_user",MODE_PRIVATE);
                    boolean logged_on = pre.getBoolean("logged_on", false);
                    getData(logged_on);
                }else { reconnect(); }
            }

            @Override
            public void onFailure(@NonNull Call<Connect> call, @NonNull Throwable t) {
                reconnect();
                Log.e("TAG - res", t.getMessage()) ;
            }
        });
    }

    private void reconnect() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(SplashScreenLogInActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(SplashScreenLogInActivity.this);
        }
        builder.setMessage("Không có kết nối!");
        builder.setPositiveButton("OK", (dialog, id) -> checkConnection());
        builder.setCancelable(false);
        builder.show();
    }

    private void getData(boolean logged_on) {

        new Handler().postDelayed(() -> {
            if (logged_on) {
                mIntent = new Intent(this, MainActivity.class);
                Disposable disposable = mStaffRepository.loadingStaff()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(staffs -> {
                            mIntent.putExtra("staff", staffs.get(0));
                            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mIntent);
                        }, throwable -> onGetAllStaffFailure(throwable.getMessage()));
                mCompositeDisposable.add(disposable);
            }else {
                mIntent = new Intent(this, LoginActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mIntent);
            }
        }, 3000);
    }

    private void onGetAllStaffFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.e("TAG - message", message);
    }
}
