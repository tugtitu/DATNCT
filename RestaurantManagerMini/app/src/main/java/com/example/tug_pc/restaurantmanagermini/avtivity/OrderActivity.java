package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerBillAdapter;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerFoodAdapter;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerKindFoodAdapter;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerOrderAdapter;
import com.example.tug_pc.restaurantmanagermini.data.LoadStaffWithRoom;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.clients.GetRes;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiBill;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiConnect;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiFood;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiKindFood;
import com.example.tug_pc.restaurantmanagermini.model.Bill;
import com.example.tug_pc.restaurantmanagermini.model.BillDetails;
import com.example.tug_pc.restaurantmanagermini.model.Food;
import com.example.tug_pc.restaurantmanagermini.model.KindFood;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.Table;
import com.example.tug_pc.restaurantmanagermini.model.response.BillResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.Connect;
import com.example.tug_pc.restaurantmanagermini.model.response.FoodResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.KindFoodResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.Result;
import com.example.tug_pc.restaurantmanagermini.ultil.Formatter;
import com.example.tug_pc.restaurantmanagermini.ultil.LoadBackground;
import com.example.tug_pc.restaurantmanagermini.ultil.Progress;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tug_pc.restaurantmanagermini.ultil.LockActivityOrientation.lockActivityOrientation;

@SuppressLint("StaticFieldLeak")
public class OrderActivity extends AppCompatActivity
        implements View.OnClickListener{
    private MyRecyclerFoodAdapter mAdapterFood;
    private List<Food> mListFood;

    private MyRecyclerOrderAdapter mAdapterOrder;
    private static List<BillDetails> sListBillDetails;

    private ProgressDialog mProgressDialog;
    private TextView mText_view_quantity;
    private TextView mText_view_price;
    private TextView mText_view_set;
    private Dialog mDialog;
    private Button mButtonPayment;

    public static Bill sPublicBill;
    private static Table sTable;
    private Staff mStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        LinearLayout linear_order = findViewById(R.id.linear_order);
        LoadBackground.loadImageWithGlide(this, linear_order, R.drawable.bg_10_min);

        lockActivityOrientation(this);

        sTable = (Table) getIntent().getSerializableExtra("table");

        setSupportActionBar(findViewById(R.id.tool_title_management));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        new GetRes().getRestaurant(getSupportActionBar(), 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadStaffWithRoom loadStaffWithRoom = LoadStaffWithRoom.getInstance(this);
        AnhXa(sTable);

        mStaff = loadStaffWithRoom.getStaff();
        if (mStaff.getKind_id() != 2) {
            mButtonPayment.setVisibility(View.VISIBLE);
            mButtonPayment.setOnClickListener(this);
        }
        showOrder();
    }

    private void showOrder() {
        ApiConnect apiConnect = ApiClient.getClient().create(ApiConnect.class);
        Call<Connect> call = apiConnect.haveConnection();
        call.enqueue(new Callback<Connect>() {
            @Override
            public void onResponse(@NonNull Call<Connect> call, @NonNull Response<Connect> response) {
                Connect connect = response.body();
                assert connect != null;
                if (connect.getSuccess()) {
                    mProgressDialog = new ProgressDialog(OrderActivity.this);
                    mProgressDialog.setTitle("Displaying data");
                    mProgressDialog.setMessage("Loading ...");
                    mProgressDialog.show();
                    sListBillDetails = new ArrayList<>();
                    RecyclerView mReOrder = findViewById(R.id.recycler_order);
                    mReOrder.setHasFixedSize(true);
                    mReOrder.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                    mAdapterOrder = new MyRecyclerOrderAdapter(OrderActivity.this, sListBillDetails, mStaff);
                    mReOrder.setItemAnimator(new DefaultItemAnimator());
                    mReOrder.setAdapter(mAdapterOrder);
                    getBillByTableId(sTable.getId());
                    getKindFood();
                    showFood();
                }else { reconnect(); }
            }

            @Override
            public void onFailure(@NonNull Call<Connect> call, @NonNull Throwable t) {
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
        builder.setPositiveButton("OK", (dialog, id) -> showOrder());
        builder.setCancelable(false);
        builder.show();
    }

    private void showFood() {
        mListFood = new ArrayList<>();
        RecyclerView recyclerFood = findViewById(R.id.recycler_food);
        recyclerFood.setHasFixedSize(true);
        recyclerFood.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapterFood = new MyRecyclerFoodAdapter(this, mListFood);
        recyclerFood.setItemAnimator(new DefaultItemAnimator());
        recyclerFood.setAdapter(mAdapterFood);

        getFood(-1);
    }

    void getKindFood() {
        ApiKindFood apiKindFood = ApiClient.getClient().create(ApiKindFood.class);
        Call<KindFoodResponse> call = apiKindFood.getAllDataKindFood();
        call.enqueue(new Callback<KindFoodResponse>() {
            @Override
            public void onResponse(@NonNull Call<KindFoodResponse> call, @NonNull Response<KindFoodResponse> response) {
                KindFoodResponse kindFoodResponse = response.body();

                assert kindFoodResponse != null;
                if (kindFoodResponse.getStatus() == 1) {
                    List<KindFood> list = new ArrayList<>();
                    list.add(new KindFood(0, "All"));
                    list.addAll(kindFoodResponse.getListKindFood());

                    MyRecyclerKindFoodAdapter kindFoodAdapter = new MyRecyclerKindFoodAdapter(OrderActivity.this, list);
                    RecyclerView recyclerKF = findViewById(R.id.recycler_food_kind);
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());

                    recyclerKF.setLayoutManager(manager);
                    recyclerKF.setItemAnimator(new DefaultItemAnimator());
                    recyclerKF.setAdapter(kindFoodAdapter);
                }else { showToast(kindFoodResponse.getMessage()); }
            }

            @Override
            public void onFailure(@NonNull Call<KindFoodResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    public void getFood(int idF) {
        ApiFood apiFood = ApiClient.getClient().create(ApiFood.class);
        Call<FoodResponse> call = apiFood.getDataFood(idF);
        call.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(@NonNull Call<FoodResponse> call, @NonNull Response<FoodResponse> response) {
                FoodResponse foodResponse = response.body();
                assert foodResponse != null;
                if (foodResponse.getStatus() == 1) {
                    mListFood = foodResponse.getListFood();
                    mAdapterFood.addData(mListFood);
                }else { showToast("Danh sách trống!"); }

                new Handler().postDelayed(mProgressDialog::dismiss, 1000);
            }

            @Override
            public void onFailure(@NonNull Call<FoodResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
                mProgressDialog.dismiss();
            }
        });
    }

    private void getBillByTableId(int idTable) {
        ApiBill apiBill = ApiClient.getClient().create(ApiBill.class);
        Call<BillResponse> call = apiBill.getOrderBar(idTable);
        call.enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(@NonNull Call<BillResponse> call, @NonNull Response<BillResponse> response) {
                BillResponse billResponse = response.body();
                assert billResponse != null;
                if (billResponse.getSuccess()) {
                    sPublicBill = billResponse.getListBill().get(0);
                    sListBillDetails = sPublicBill.getListBillDetails();
                    displayBill(sPublicBill.getId(), sPublicBill.getTotal_price(), sListBillDetails.size());
                }else {
                    sPublicBill = null;
                    sListBillDetails.clear();
                    displayBill(-1, 0, 0);
                }
                mAdapterOrder.addData(sListBillDetails);
                invalidateOptionsMenu();
            }

            @Override
            public void onFailure(@NonNull Call<BillResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void displayBill(int idBill, long total, int size) {
        if (idBill != -1) {
            String strIdOrder = "B";
            if (idBill < 10) {
                strIdOrder += "000" + idBill;
            }else if (idBill < 100) {
                strIdOrder += "00" + idBill;
            }else if (idBill < 1000) {
                strIdOrder += "0" + idBill;
            }else {
                strIdOrder += idBill;
            }
            ((TextView)findViewById(R.id.text_order_id)).setText(strIdOrder);
        }else { ((TextView)findViewById(R.id.text_order_id)).setText(""); }
        ((TextView)findViewById(R.id.text_qty)).setText(String.valueOf(size));
        ((TextView)findViewById(R.id.text_total)).setText(String.valueOf(total));
    }

    public void updateOrderInfo(int idBill, int idFood, int idInfo, int isPlus, String note){
        if (mStaff.get_id() != 3 || mStaff.get_id() != 1) {
            ApiBill apiBill = ApiClient.getClient().create(ApiBill.class);
            Call<Result> call =
                    apiBill.updateDataBillDetails(idBill, sTable.getId(), mStaff.get_id(), idFood, idInfo, isPlus, note);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                    Result result = response.body();
                    assert result != null;
                    if (result.getSuccess()) {
                        getBillByTableId(sTable.getId());
                    }else {
                        showToast(result.getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                    showToast(t.getMessage());
                }
            });
        }else { showToast("Bạn không thể thực hiện chức năng này"); }
    }

    public void dialogConfirm(Context context, int is) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(builder.getContext().getResources().getString(R.string.action_confirm_merge));
        String message = builder.getContext().getResources().getString(R.string.msg_are_you_sure) + " ";
        switch (is) {
            case 1: {
                message +=  builder.getContext().getResources().getString(R.string.msg_create);
                break;
            }

            case 2: {
                message += builder.getContext().getResources().getString(R.string.msg_cancel);
                break;
            }

            case 3: {
                message += builder.getContext().getResources().getString(R.string.msg_send_require);
                break;
            }

            case 4: {
                message += builder.getContext().getResources().getString(R.string.msg_notification) + " ?";
                break;
            }
        }
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, id) -> {
            mProgressDialog.show();
            updateBill(sPublicBill.getId(), sTable.getId(), is);
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> { });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

    private void updateBill(int idBill, int idTable, int is) {
        ApiBill apiBill = ApiClient.getClient().create(ApiBill.class);
        Call<Result> call = apiBill.updateBill(idBill, idTable, is);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                Result result = response.body();
                assert result != null;
                if (result.getSuccess()) {
                    getBillByTableId(idTable);
                }else { showToast(result.getMessage()); }

                new Handler().postDelayed(mProgressDialog::dismiss, 1000);
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                showToast(t.getMessage());
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_title_management, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (sPublicBill != null) {
            menu.findItem(R.id.item_root_list).setVisible(true);
            if (sListBillDetails == null || sListBillDetails.size() == 0) {
                menu.findItem(R.id.item_create).setVisible(false);
            }
        }else {
            menu.findItem(R.id.item_root_list).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Do your actions here
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.item_reboot:
                onRestart();
                return true;
            case R.id.item_create: {
                dialogConfirm(this, 1);
                return true;
            }

            case R.id.item_cancel: {
                dialogConfirm(this, 2);
                return true;
            }

            case R.id.item_print: {
                dialogConfirm(this, 3);
                return true;
            }
        }
        return true;
    }

    private void AnhXa(Table table) {
        mButtonPayment = findViewById(R.id.btn_payment);
        TextView textTable = findViewById(R.id.text_table_name);
        textTable.setText(table.getName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showOrder();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_management);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (sPublicBill != null){
            if (mStaff.getKind_id() == 3 || mStaff.getKind_id() == 1) {
                if (sTable.getStatus() != 3) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                        }else {
                            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                        }
                    } else {
                        builder = new AlertDialog.Builder(this);
                    }
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setTitle("Xác nhận!");
                    String message = "Hãy kiểm tra để chắc chắn rằng bàn này được phép thanh toán!";

                    builder.setMessage(message);
                    builder.setPositiveButton("OK", (dialog, id) -> { });
                    builder.setNegativeButton("Xác nhận thanh toán", (dialog, id) -> showBillDialog(sPublicBill));

                    builder.show();
                }else { showBillDialog(sPublicBill); }
            }
            else { showToast("Bạn không thể thực hiện chức năng này"); }
        }else {
            showToast("Bạn chưa gọi món");
        }
    }

    private void showBillDialog(Bill bill) {
        List<BillDetails> infoList = bill.getListBillDetails();
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bill_show);

        dialog.findViewById(R.id.text_title_bill).setVisibility(View.GONE);
        dialog.findViewById(R.id.text_address_bill).setVisibility(View.GONE);
        dialog.findViewById(R.id.text_phone_bill).setVisibility(View.GONE);
        dialog.findViewById(R.id.view).setVisibility(View.GONE);
        dialog.findViewById(R.id.text_customer).setVisibility(View.GONE);
        dialog.findViewById(R.id.text_customer_name).setVisibility(View.GONE);
        dialog.findViewById(R.id.view_w_w).setVisibility(View.GONE);

        dialog.findViewById(R.id.linear_bill_child1).setVisibility(View.GONE);
        dialog.findViewById(R.id.linear_bill_child2).setVisibility(View.GONE);

        TextView txtTotal = dialog.findViewById(R.id.text_total_price);
        txtTotal.setVisibility(View.VISIBLE);
        TextView txtDate = dialog.findViewById(R.id.text_date);
        TextView btn_intent = dialog.findViewById(R.id.btn_intent);

        txtDate.setText(bill.getTime_order());
        long total = 0;
        for (int i = 0; i < infoList.size(); i++) {
            BillDetails info = infoList.get(i);
            total += (info.getPrice() - (info.getPrice() * info.getPromotions() / 100)) * info.getQuantity();
        }
        txtTotal.setText(Formatter.format(total));

        RecyclerView recycler = dialog.findViewById(R.id.recycler_bill);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        recycler.setAdapter(new MyRecyclerBillAdapter(dialog.getContext(), infoList));

        dialog.findViewById(R.id.btn_in).setVisibility(View.VISIBLE);
        dialog.findViewById(R.id.btn_in).setOnClickListener(v -> {
            showToast("In phiếu");
            dialog.dismiss();
        });

        String ok = "Xác nhận thanh toán!";
        btn_intent.setText(ok);
        btn_intent.setOnClickListener(v -> {
            LinearLayout mOrderFormView = findViewById(R.id.order_form);
            ProgressBar mProgressView = findViewById(R.id.order_progress);
            Progress progress = new Progress(this, mOrderFormView, mProgressView);
            Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bill", bill);
            bundle.putSerializable("table", sTable);
            bundle.putSerializable("staff", mStaff);
            intent.putExtra("bundle", bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            progress.showProgress(true);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                dialog.dismiss();
                startActivity(intent);
                progress.showProgress(false);
            }, 1000);
        });
        dialog.show();
    }

    public void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

//    public void dialogAddFast() {
//        mDialog = new Dialog(this);
//        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.setContentView(R.layout.dialog_custom_add_fast);
//
//        EditText edtInput = mDialog.findViewById(R.id.edit_input_name_food);
//        final InputMethodManager imm = (InputMethodManager) mDialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        edtInput.setOnFocusChangeListener((v1, isFocused) -> {
//            if (isFocused) {
//                Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
//            }
//        });
//
//        mText_view_quantity = mDialog.findViewById(R.id.text_quantity);
//        mText_view_set = mText_view_quantity;
//        mText_view_set.setSelected(true);
//        mText_view_price = mDialog.findViewById(R.id.text_price);
//
//        Button button_confirm = mDialog.findViewById(R.id.btn_confirm);
//        button_confirm.setOnClickListener(v12 -> mDialog.cancel());
//
//        Button button_cancel = mDialog.findViewById(R.id.btn_cancel);
//        button_cancel.setOnClickListener(v13 -> mDialog.cancel());
//        mDialog.show();
//    }

    public void onClick_abacus(View v) {
        showToast(mText_view_set.getText().toString() +" - "+ ((TextView)v).getText().toString());
    }

    public void onClickEdit(View v) {
        mText_view_set = (TextView) v;

        mText_view_quantity.setSelected(false);
        mText_view_price.setSelected(false);
        mText_view_quantity.setPressed(false);
        mText_view_price.setPressed(false);

        mText_view_set.setSelected(true);
        mText_view_set.setPressed(false);
    }
}
