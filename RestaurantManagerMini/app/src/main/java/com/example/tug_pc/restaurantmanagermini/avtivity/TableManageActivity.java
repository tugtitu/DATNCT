package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerAreaManageAdapter;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerTableManageAdapter;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiArea;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiConnect;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiTable;
import com.example.tug_pc.restaurantmanagermini.model.Area;
import com.example.tug_pc.restaurantmanagermini.model.Table;
import com.example.tug_pc.restaurantmanagermini.model.response.AreaResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.Connect;
import com.example.tug_pc.restaurantmanagermini.model.response.TableResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableManageActivity extends AppCompatActivity {
    private List<Area> mAreas;
    private MyRecyclerAreaManageAdapter mAreaAdapter;
    private MyRecyclerTableManageAdapter mTableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_manage);

        mAreas = new ArrayList<>();
        RecyclerView recyclerArea = findViewById(R.id.recycler_category);
        recyclerArea.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL, false));
        mAreaAdapter = new MyRecyclerAreaManageAdapter(this);
        recyclerArea.setAdapter(mAreaAdapter);

        RecyclerView recyclerTable = findViewById(R.id.recycler_child);
        recyclerTable.setHasFixedSize(true);
        int spanCount = 2;
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 3;
        }
        recyclerTable.setLayoutManager(new GridLayoutManager(this, spanCount));
        mTableAdapter = new MyRecyclerTableManageAdapter(this);
        recyclerTable.setAdapter(mTableAdapter);
        checkConnection();

        findViewById(R.id.floating_ac_btn_exit).setOnClickListener(v -> onBackPressed());
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
                    displayTable();
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

    public void displayTable() {
        if (mAreas.size() > 0) mAreas.clear();
        ApiArea apiArea = ApiClient.getClient().create(ApiArea.class);
        Call<AreaResponse> call = apiArea.displayArea();
        call.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(@NonNull Call<AreaResponse> call, @NonNull Response<AreaResponse> response) {
                AreaResponse areaResponse = response.body();
                assert areaResponse != null;
                if (areaResponse.getSuccess()) {
                    List<Area> areaList = new ArrayList<>();
                    areaList.add(new Area(-1, TableManageActivity.this.getResources().getString(R.string.action_add)));
                    mAreas.addAll(areaResponse.getListArea());
                    areaList.addAll(mAreas);
                    mAreaAdapter.addData(areaList);
                    getTableByArea(mAreas.get(0).getId(), mAreas.get(0).getName());
                }else { makeToast(areaResponse.getMessage()); }
            }

            @Override
            public void onFailure(@NonNull Call<AreaResponse> call, @NonNull Throwable t) {
                makeToast(t.getMessage());
            }
        });
    }

    public void getTableByArea(int id, String name) {
        ApiTable apiTable = ApiClient.getClient().create(ApiTable.class);
        Call<TableResponse> call = apiTable.getDataManageTable(id);
        call.enqueue(new Callback<TableResponse>() {
            @Override
            public void onResponse(@NonNull Call<TableResponse> call, @NonNull Response<TableResponse> response) {
                TableResponse tableResponse = response.body();
                assert tableResponse != null;
                List<Table> mListTable = new ArrayList<>();
                mListTable.add(new Table(-1, TableManageActivity.this.getResources().getString(R.string.action_add), name));
                if (tableResponse.getSuccess()) {
                    mListTable.addAll(tableResponse.getListTable());
                }

                mTableAdapter.addData(mListTable, mAreas);
            }

            @Override
            public void onFailure(@NonNull Call<TableResponse> call, @NonNull Throwable t) {
                makeToast(t.getMessage());
            }
        });
    }

    public void makeToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
