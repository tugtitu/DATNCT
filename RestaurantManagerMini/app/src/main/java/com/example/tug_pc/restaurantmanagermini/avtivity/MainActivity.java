package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerMainAdapter;
import com.example.tug_pc.restaurantmanagermini.model.Restaurant;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.response.RestaurantResponse;
import com.example.tug_pc.restaurantmanagermini.ultil.LoadBackground;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiRestaurant;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static Staff sStaff;
    private static Restaurant sRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linear_main = findViewById(R.id.linear_main);
        LoadBackground.loadImageWithGlide(this, linear_main, R.drawable.bg_10_min);

        Toolbar mToolbar = findViewById(R.id.tool_title_main);
        setSupportActionBar(mToolbar);
        sStaff = (Staff) getIntent().getSerializableExtra("staff");

        if (sStaff != null) {
            getRestaurant(mToolbar, sStaff.getResId());
        }
    }

    private void getRestaurant(Toolbar toolbar, int idRes) {
        ApiRestaurant apiRestaurant = ApiClient.getClient().create(ApiRestaurant.class);
        Call<RestaurantResponse> call = apiRestaurant.getRestaurant(idRes);
        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantResponse> call, @NonNull Response<RestaurantResponse> response) {
                RestaurantResponse res = response.body();
                assert res != null;
                if (res.getSuccess()) {
                    sRestaurant = res.getListRestaurant().get(0);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(sRestaurant.getRes_name());
                    showNavigationMain(toolbar);

                    showListItemMain();
                } else {
                    Objects.requireNonNull(getSupportActionBar()).setTitle("asaasa");
                    Toast.makeText(MainActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantResponse> call, @NonNull Throwable t) {
                Log.e("TAG - res", t.getMessage()) ;
            }
        });
    }

    private void showListItemMain() {
        RecyclerView recyclerView = findViewById(R.id.recycler_main_list);
        int spanCount = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        MyRecyclerMainAdapter adapter = new MyRecyclerMainAdapter(MainActivity.this, sStaff, sRestaurant);
        recyclerView.setAdapter(adapter);
    }

    private void showNavigationMain(Toolbar toolbar) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.msg_nav_drawer_open, R.string.msg_nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navi_main);
        View mHeaderView = navigationView.getHeaderView(0);
        TextView title = mHeaderView.findViewById(R.id.text_nav_title);
        title.setText(sRestaurant.getRes_name());

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sStaff = null;
        sRestaurant = null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.image_btn_edit_res) {
            //new activity
//        } else if (id == R.id.item_sync_data) {
//            //new activity
//        } else if (id == R.id.item_shop_info) {
//            //new activity thông tin nhà hàng
//        } else if (id == R.id.item_set_up_printer) {
//            //new activity cài đặt máy in
//        } else if (id == R.id.item_promotions) {
//            //new activity khuyến mãi
//        } else if (id == R.id.item_customer) {
//            //new activity khách hàng
//        } else if (id == R.id.item_periodic_statistics) {
            //new activity doanh thu định kỳ
        } else if (id == R.id.item_logout) {
            //trở về trang đăng nhập
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle(this.getResources().getString(R.string.msg_notification));
            builder.setMessage("Bạn muốn đăng xuất?");
            builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                startActivity(new Intent(this, LoginActivity.class
                ).putExtra("logout", true).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            });
            builder.setNegativeButton("Hủy", (dialogInterface, i) -> {
            });
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
