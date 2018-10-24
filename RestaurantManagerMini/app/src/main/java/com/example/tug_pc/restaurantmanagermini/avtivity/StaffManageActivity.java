package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerStaffManageAdapter;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.response.Connect;
import com.example.tug_pc.restaurantmanagermini.model.response.StaffResponse;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiConnect;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiStaff;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffManageActivity extends AppCompatActivity {
    private MyRecyclerStaffManageAdapter mAdapter;
    private List<Staff> mListStaff;
    private static Staff sStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manage);

        sStaff = (Staff) getIntent().getSerializableExtra("staff");

        onStaffManage();

        findViewById(R.id.image_btn_edit).setOnClickListener(v -> startUpdateInformationStaffActivity());

        findViewById(R.id.floating_ac_btn_exit).setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void startUpdateInformationStaffActivity() {
        if (sStaff != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("staff", sStaff);
            bundle.putBoolean("isEdit", true);
            startActivity(
                    new Intent(this, UpdateInformationStaffActivity.class)
                            .putExtra("bundle_staff", bundle)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    private void onStaffManage() {
        if (sStaff != null) {
            TextView text_view_name = findViewById(R.id.text_staff_root_name);
            text_view_name.setText(sStaff.getName());
            TextView text_view_address = findViewById(R.id.text_staff_root_address);
            text_view_address.setText(sStaff.getAddress());
            TextView text_view_phone = findViewById(R.id.text_staff_root_phone);
            text_view_phone.setText(sStaff.getPhone());

            mListStaff = new ArrayList<>();
            mAdapter = new MyRecyclerStaffManageAdapter(this);
            RecyclerView recycler = findViewById(R.id.recycler_child);
            recycler.setHasFixedSize(true);
            recycler.setBackground(this.getResources().getDrawable(R.drawable.bg_staff_child));
            recycler.setLayoutManager(new LinearLayoutManager(this));
            recycler.setAdapter(mAdapter);
            checkConnection();
        }
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
                    getStaffChild(sStaff.get_id());
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
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setMessage("Không có kết nối!");
        builder.setPositiveButton("OK", (dialog, id) -> checkConnection());
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onStaffManage();
    }

    public void getStaffChild(int idStaff) {
        if (mListStaff.size() > 0) {
            mListStaff.clear();
        }
        ApiStaff apiStaff = ApiClient.getClient().create(ApiStaff.class);
        Call<StaffResponse> call = apiStaff.getStaffChildByRoot(idStaff);
        call.enqueue(new Callback<StaffResponse>() {
            @Override
            public void onResponse(@NonNull Call<StaffResponse> call, @NonNull Response<StaffResponse> response) {
                StaffResponse staffResponse = response.body();
                assert staffResponse != null;
                if (staffResponse.getSuccess()) {
                    mListStaff.add(new Staff(-1, getString(R.string.action_add)));
                    mListStaff.addAll(staffResponse.getListStaff());
                    mAdapter.addData(mListStaff);
                }else { showToast(staffResponse.getMessage()); }
            }

            @Override
            public void onFailure(@NonNull Call<StaffResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
