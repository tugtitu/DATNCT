package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerKitchenOrderAdapter;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiBill;
import com.example.tug_pc.restaurantmanagermini.model.Bill;
import com.example.tug_pc.restaurantmanagermini.model.response.BillResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KitchenActivity extends AppCompatActivity {
    private static final String TAG = KitchenActivity.class.getSimpleName();
    private MyRecyclerKitchenOrderAdapter mAdapter;
    private RecyclerView mRecycler;
    private TextView mText_notification;
    private List<Bill> mOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        Toolbar toolbar = findViewById(R.id.tool_title_kitchen);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.msg_bar));

        mRecycler = findViewById(R.id.recycler_order);
        mRecycler.setLayoutManager(new LinearLayoutManager(KitchenActivity.this));
        mAdapter = new MyRecyclerKitchenOrderAdapter(KitchenActivity.this, mOrders);
        mRecycler.setAdapter(mAdapter);
        mText_notification = findViewById(R.id.text_notification);

        getOrderBar();
    }

    public void getOrderBar() {
        ApiBill apiBill = ApiClient.getClient().create(ApiBill.class);
        Call<BillResponse> call = apiBill.getOrderBar(-1);
        call.enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(@NonNull Call<BillResponse> call, @NonNull Response<BillResponse> response) {
                BillResponse billResponse = response.body();
                assert billResponse != null;
                if (billResponse.getSuccess()) {
                    mOrders = billResponse.getListBill();
                    mText_notification.setVisibility(View.GONE);
                    mRecycler.setVisibility(View.VISIBLE);
                    mAdapter.addData(mOrders);
                }else {
                    mText_notification.setVisibility(View.VISIBLE);
                    mRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BillResponse> call, @NonNull Throwable t) {
                Toast.makeText(KitchenActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return false;
    }
}
