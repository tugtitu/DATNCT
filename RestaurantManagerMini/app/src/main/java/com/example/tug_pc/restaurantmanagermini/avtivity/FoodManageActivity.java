package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerFoodManageAdapter;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerKindFoodManageAdapter;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiConnect;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiFood;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiKindFood;
import com.example.tug_pc.restaurantmanagermini.model.Food;
import com.example.tug_pc.restaurantmanagermini.model.KindFood;
import com.example.tug_pc.restaurantmanagermini.model.response.Connect;
import com.example.tug_pc.restaurantmanagermini.model.response.FoodResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.KindFoodResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodManageActivity extends AppCompatActivity {
    private MyRecyclerKindFoodManageAdapter mCategoryFoodAdapter;
    private MyRecyclerFoodManageAdapter mFoodAdapter;
    List<KindFood> mKindFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_manage);

        mKindFood = new ArrayList<>();
        RecyclerView recyclerKindFood = findViewById(R.id.recycler_category);
        recyclerKindFood.setLayoutManager(
                new LinearLayoutManager(
                        FoodManageActivity.this,
                        LinearLayoutManager.HORIZONTAL, false));
        mCategoryFoodAdapter = new MyRecyclerKindFoodManageAdapter(FoodManageActivity.this);
        recyclerKindFood.setAdapter(mCategoryFoodAdapter);

        RecyclerView recyclerFood = findViewById(R.id.recycler_child);
        recyclerFood.setLayoutManager(
                new LinearLayoutManager(
                        FoodManageActivity.this,
                        LinearLayoutManager.VERTICAL, false));
        mFoodAdapter = new MyRecyclerFoodManageAdapter(FoodManageActivity.this);
        recyclerFood.setAdapter(mFoodAdapter);
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
                    displayFood();
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

    public void displayFood() {
        if (mKindFood.size() > 0) mKindFood.clear();
        ApiKindFood apiKindFood = ApiClient.getClient().create(ApiKindFood.class);
        Call<KindFoodResponse> call = apiKindFood.getAllDataKindFood();
        call.enqueue(new Callback<KindFoodResponse>() {
            @Override
            public void onResponse(@NonNull Call<KindFoodResponse> call, @NonNull Response<KindFoodResponse> response) {
                KindFoodResponse kfResponse = response.body();
                assert kfResponse != null;
                if (kfResponse.getStatus() ==1) {
                    List<KindFood> kindFoodList = new ArrayList<>();
                    kindFoodList.add(new KindFood(-1, FoodManageActivity.this.getResources().getString(R.string.action_add)));
                    mKindFood.addAll(kfResponse.getListKindFood());
                    kindFoodList.addAll(mKindFood);
                    mCategoryFoodAdapter.addData(kindFoodList);
                    getFoodByCategory(mKindFood.get(0).getId(), mKindFood.get(0).getKind_name());
                }else { makeToast(kfResponse.getMessage()); }
            }

            @Override
            public void onFailure(@NonNull Call<KindFoodResponse> call, @NonNull Throwable t) {
                makeToast(t.getMessage());
            }
        });
    }

    public void getFoodByCategory(int id, String name) {
        ApiFood apiFood = ApiClient.getClient().create(ApiFood.class);
        Call<FoodResponse> call = apiFood.getDataFood(id);
        call.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(@NonNull Call<FoodResponse> call, @NonNull Response<FoodResponse> response) {
                FoodResponse foodResponse = response.body();
                assert foodResponse != null;
                List<Food> mListFood = new ArrayList<>();
                mListFood.add(new Food(-1, getString(R.string.action_add), name));
                if (foodResponse.getStatus() == 1) {
                    mListFood.addAll(foodResponse.getListFood());
                }
                mFoodAdapter.addData(mListFood, mKindFood);
            }

            @Override
            public void onFailure(@NonNull Call<FoodResponse> call, @NonNull Throwable t) {
                makeToast(t.getMessage());
            }
        });
    }

    public void makeToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
