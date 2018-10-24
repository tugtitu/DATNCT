package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerAreaAdapter;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerTableAdapter;
import com.example.tug_pc.restaurantmanagermini.data.LoadStaffWithRoom;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.clients.GetRes;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiArea;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiConnect;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiTable;
import com.example.tug_pc.restaurantmanagermini.model.Area;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.Table;
import com.example.tug_pc.restaurantmanagermini.model.response.AreaResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.Connect;
import com.example.tug_pc.restaurantmanagermini.model.response.TableResponse;
import com.example.tug_pc.restaurantmanagermini.ultil.LoadBackground;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleActivity extends AppCompatActivity{
    private static final String TAG = SaleActivity.class.getSimpleName();

    private MyRecyclerTableAdapter mTableAdapter;
    private List<Table> mListTable;

    private LoadStaffWithRoom mLoadStaffWithRoom;
    private ProgressDialog mProgressDialog;
    public static int isArea = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        LinearLayout linear_sale = findViewById(R.id.linear_sale);
        LoadBackground.loadImageWithGlide(this, linear_sale, R.drawable.bg_10_min);

        setSupportActionBar(findViewById(R.id.tool_title_sale));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        new GetRes().getRestaurant(getSupportActionBar(), 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoadStaffWithRoom = LoadStaffWithRoom.getInstance(this);
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
                    mProgressDialog = new ProgressDialog(SaleActivity.this);
                    mProgressDialog.setTitle("Displaying data");
                    mProgressDialog.setMessage(getString(R.string.msg_loading));
                    mProgressDialog.show();
                    getAllArea();
                    showTableList();
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
            builder = new AlertDialog.Builder(SaleActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(SaleActivity.this);
        }
        builder.setMessage("Không có kết nối!");
        builder.setPositiveButton("OK", (dialog, id) -> checkConnection());
        builder.setCancelable(false);
        builder.show();
    }

    private void showTableList() {
        Staff mStaff = mLoadStaffWithRoom.getStaff();
        mListTable = new ArrayList<>();
        RecyclerView recyclerTable = findViewById(R.id.recycler_table);
        recyclerTable.setHasFixedSize(true);
        int spanCount = 2;
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 3;
        }
        recyclerTable.setLayoutManager(new GridLayoutManager(this, spanCount));
        mTableAdapter = new MyRecyclerTableAdapter(this, mListTable, mStaff);
        recyclerTable.setItemAnimator(new DefaultItemAnimator());
        recyclerTable.setAdapter(mTableAdapter);

        getTableByAreaId(isArea);
    }

    void getAllArea() {
        ApiArea apiArea = ApiClient.getClient().create(ApiArea.class);
        Call<AreaResponse> call = apiArea.displayArea();
        call.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(@NonNull Call<AreaResponse> call, @NonNull Response<AreaResponse> response) {
                AreaResponse areaResponse = response.body();
                assert areaResponse != null;
                if (areaResponse.getSuccess()) {
                    List<Area> list = new ArrayList<>();
                    list.add(new Area(0, getString(R.string.action_empty)));
                    list.addAll(areaResponse.getListArea());
                    MyRecyclerAreaAdapter areaAdapter = new MyRecyclerAreaAdapter(SaleActivity.this, list);
                    RecyclerView recyclerAreaT = findViewById(R.id.recycler_areaT);
                    recyclerAreaT.setLayoutManager(
                            new LinearLayoutManager(
                                    getApplicationContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false));
                    recyclerAreaT.setItemAnimator(new DefaultItemAnimator());
                    recyclerAreaT.setAdapter(areaAdapter);
                    mProgressDialog.dismiss();
                }else {
                    Toast.makeText(SaleActivity.this, areaResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AreaResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
                mProgressDialog.dismiss();
            }
        });
    }

    public void getTableByAreaId(int id) {
        isArea = id;
        ApiTable apiTable = ApiClient.getClient().create(ApiTable.class);
        Call<TableResponse> call = apiTable.getDataTable(id);
        call.enqueue(new Callback<TableResponse>() {
            @Override
            public void onResponse(@NonNull Call<TableResponse> call, @NonNull Response<TableResponse> response) {
                TableResponse tableResponse = response.body();
                assert tableResponse != null;
                if (tableResponse.getSuccess()) {
                    mListTable = tableResponse.getListTable();
                }else {
                    mListTable = new ArrayList<>();
                }
                mTableAdapter.addData(mListTable);
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<TableResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getTableByAreaId(isArea);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_order);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isArea = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_order_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.orderReset:
                onRestart();
                return true;
        }

        return false;
    }
}
