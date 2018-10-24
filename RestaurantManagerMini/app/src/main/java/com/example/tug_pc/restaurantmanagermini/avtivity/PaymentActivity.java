package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerBillAdapter;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiBill;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiRestaurant;
import com.example.tug_pc.restaurantmanagermini.model.Bill;
import com.example.tug_pc.restaurantmanagermini.model.BillDetails;
import com.example.tug_pc.restaurantmanagermini.model.Restaurant;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.response.BillResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.RestaurantResponse;
import com.example.tug_pc.restaurantmanagermini.ultil.Formatter;
import com.example.tug_pc.restaurantmanagermini.ultil.LoadBackground;
import com.example.tug_pc.restaurantmanagermini.ultil.Progress;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tug_pc.restaurantmanagermini.ultil.LockActivityOrientation.lockActivityOrientation;

public class PaymentActivity extends AppCompatActivity {
    private EditText mEditCurrentT, mEditDiscount, mEditSurcharge, mEditIntoMoney, mEditCash, mEditChange, mEditShip;
    private Bundle BUNDLE_MANAGEMENT;
    private EditText mEdtInput;
    private ViewGroup mViewGroup;
    private View mView;

    private Restaurant mRes;
    private static Bill sBill;
    private static Staff sStaff;
    private static String sNote = "";
    private static String sCustomer = "";

    private long input = 0;
    private long into_money = 0;
    private long cash = 0;
    private long change = 0;
    private long discount = 0;
    private long surcharge = 0;
    private long ship = 0;
    private long currentTotal = 0;
    private int mPayment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        LinearLayout linear_payment = findViewById(R.id.linear_payment);
        LoadBackground.loadImageWithGlide(this, linear_payment, R.drawable.bg_10_min);

        lockActivityOrientation(this);

        setSupportActionBar(findViewById(R.id.tool_title_payment));
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AnhXa();

        BUNDLE_MANAGEMENT = getIntent().getBundleExtra("bundle");

        mViewGroup = findViewById(R.id.payment_form);
        mView = findViewById(R.id.payment_progress);

        editSetText();
    }

    @SuppressLint("DefaultLocale")
    private void editSetText() {
        newEdit();
        if (currentTotal != 0) {
            mEditCurrentT.setText(Formatter.format(currentTotal));
            mEditIntoMoney.setText(Formatter.format(into_money));
        }
        mEditDiscount.setText(String.format("-%s", Formatter.format(discount)));
        mEditSurcharge.setText(String.format("+%s", Formatter.format(surcharge)));
        mEditCash.setText(Formatter.format(cash));
        mEditChange.setText(Formatter.format(change));
        mEditShip.setText(String.format("+%s", Formatter.format(ship)));
    }

    private void newEdit() {
        discount = 0;
        sBill = (Bill) BUNDLE_MANAGEMENT.getSerializable("bill");
        sStaff = (Staff) BUNDLE_MANAGEMENT.getSerializable("staff");
        assert sStaff != null;
        getRestaurant(sStaff.getResId());
        assert sBill != null;
        currentTotal = sBill.getTotal_price();
        into_money = currentTotal - discount;
        cash = 0;
        change = cash - into_money;
        surcharge = 0;
        ship = 0;
        sCustomer = "";
        sNote = "";
    }

    private void setTextEditCash(long strSub) {
        try {
            mEditCash.setText(Formatter.format(strSub));
            cash = strSub;
        }catch (Exception e1) { showToast(getString(R.string.msg_warning), Toast.LENGTH_SHORT); }

        change = strSub - into_money;

        mEditChange.setText(Formatter.format(change));
    }

    public void onClickAbacus(View view) {
        try {
            switch (view.getId()) {
                case R.id.image_btn_delete: {
                    int end = String.valueOf(cash).length()-4;
                    String substring = "0";
                    if (cash % 1000 != 0) {
                        substring = String.valueOf(cash % 1000);
                    }
                    if (end != 0) {
                        substring = String.valueOf(cash).substring(0, end);
                    }
                    setTextEditCash(Long.parseLong(substring + "000"));
                    break;
                }

                case R.id.text_exact: {
                    setTextEditCash(into_money);
                    break;
                }

                case R.id.text_delete_all: {
                    editSetText();
                    break;
                }

                case R.id.text_payment: {
                    showAlertDialog();
                    break;
                }

                default: {
                    TextView txt = findViewById(view.getId());
                    int in = Integer.parseInt(String.valueOf(txt.getText()));
                    long ca = Long.parseLong(String.valueOf(cash/1000) + String.valueOf(in)) * 1000;
                    setTextEditCash(ca);
                    break;
                }
            }
        }catch (Exception ex) {
            showToast("Nhập sai", Toast.LENGTH_SHORT);
        }
    }

    public void onClickEditText(View v) {
        switch (v.getId()){
            case R.id.edit_discount: { showEditDialog(-1, discount); break; }

            case R.id.edit_surcharge: { showEditDialog(1, surcharge); break; }

            case R.id.edit_ship: { showEditDialog(0, ship); break; }
        }
    }

    private void showEditDialog(int is, long count) {
        input = count;
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_edittext);

        mEdtInput = dialog.findViewById(R.id.edit_input);

        mEdtInput.setText(String.valueOf(count).equals("0") ? "" : String.valueOf(count));

        final InputMethodManager imm = (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mEdtInput.setOnFocusChangeListener((v1, isFocused) -> {
            if (isFocused) {
                Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        });

        mEdtInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    long l = Long.parseLong(mEdtInput.getText().toString().isEmpty() ? "0" : mEdtInput.getText().toString().trim());
                    if (l != input) {
                        input = l;
                    }
                }catch (Exception e) {
                    showToast("Sai định dạng hoặc vượt quá kích thước nhập cho phép!", Toast.LENGTH_SHORT);
                }
            }
        });

        dialog.findViewById(R.id.btn_ok).setOnClickListener(v12 -> {
            int idEdit = 0;
            String format = "+%s";

            switch (is){
                case -1: {
                    idEdit = mEditDiscount.getId();
                    format = "-%s";
                    break;
                }

                case 1: {
                    idEdit = mEditSurcharge.getId();
                    break;
                }

                case 0: {
                    idEdit = mEditShip.getId();
                    break;
                }
            }
            dialogButtonOk(idEdit, format, is, dialog);
            input = 0;
        });

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(v13 -> {
            input = 0;
            dialog.cancel();
        });
        dialog.show();
    }

    private void dialogButtonOk(int id, String format, int is, Dialog dialog) {
        EditText editText = findViewById(id);
        try {
            if (input != 0){
                editText.setText(String.format(format, Formatter.format(input)));
                switch (is){
                    case -1: {
                        if (input != discount) {
                            if (input < into_money) {
                                discount = input;
                                into_money -= discount;
                            }else { showToast(getString(R.string.msg_message_warning), Toast.LENGTH_SHORT); }
                        }
                        break;
                    }

                    case 1: {
                        if (input != surcharge) {
                            surcharge = input;
                            into_money += surcharge;
                        }
                        break;
                    }

                    case 0: {
                        if (input != ship) {
                            ship = input;
                            into_money += ship;
                        }
                        break;
                    }
                }
            }else { editText.setText(String.format(format, Formatter.format(0))); }
            change = cash - into_money;
            mEditIntoMoney.setText(Formatter.format(into_money));
            mEditChange.setText(Formatter.format(change));
        }catch (Exception e1){ showToast(getString(R.string.msg_warning), Toast.LENGTH_SHORT); }

        dialog.cancel();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(this.getResources().getString(R.string.msg_notification));
        if (mPayment == 0) {
            builder.setMessage("Bạn muốn thanh toán hóa đơn này?");
            builder.setPositiveButton("OK", (dialog, id) -> paymentCheck());
            builder.setNegativeButton("Cancel", (dialog, id) -> {});
        }else {
            builder.setMessage("Hóa đơn này đã thanh toán xong, nhấn OK để thoát.");
            builder.setPositiveButton("OK", (dialog, id) -> exitActivity());
        }
        builder.show();
    }

    private void paymentCheck() {
        Log.e("cash  -  into_money", String.valueOf(cash < into_money));
        if (cash < into_money) {
            showToast("Chưa có chức năng ghi nợ!", Toast.LENGTH_LONG);
        }else {
            billPayment();
        }
    }

    private void billPayment() {
        ApiBill apiBill = ApiClient.getClient().create(ApiBill.class);
        Call<BillResponse> call =
                apiBill.billPayment(sBill.getId(), sStaff.get_id(), sBill.getTable_id(),
                        currentTotal, cash, change, discount, surcharge, ship,
                        sCustomer.trim().isEmpty() ? "Không có thông tin" : sCustomer, sNote.trim().isEmpty() ? "Không" : sNote);
        call.enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(@NonNull Call<BillResponse> call, @NonNull Response<BillResponse> response) {
                BillResponse billResponse = response.body();
                assert billResponse != null;
                if (billResponse.getSuccess()) {
                     sBill = billResponse.getListBill().get(0);
                     dialogBill(sBill);
                }else { showToast(billResponse.getMessage(), Toast.LENGTH_SHORT); }
            }

            @Override
            public void onFailure(@NonNull Call<BillResponse> call, @NonNull Throwable t) {
                Log.e("TAG - billPayment", t.getMessage());
            }
        });
    }

    private void dialogBill(Bill bill) {
        mPayment = 1;
        List<BillDetails> infoList = bill.getListBillDetails();
        Dialog dialogBill = new Dialog(this);
        dialogBill.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBill.setContentView(R.layout.dialog_bill_show);
        dialogBill.setCanceledOnTouchOutside(false);

        TextView txtTitle = dialogBill.findViewById(R.id.text_title_bill);
        TextView txtAddress = dialogBill.findViewById(R.id.text_address_bill);
        TextView txtPhone = dialogBill.findViewById(R.id.text_phone_bill);
        TextView txtDate = dialogBill.findViewById(R.id.text_date);

        TextView txtTotal = dialogBill.findViewById(R.id.text_bill_total_price);
        TextView txtCash = dialogBill.findViewById(R.id.text_bill_cash);
        TextView txtChange = dialogBill.findViewById(R.id.text_bill_change);
        TextView txtDiscount = dialogBill.findViewById(R.id.text_bill_discount);
        TextView txtSurcharge = dialogBill.findViewById(R.id.text_bill_surcharge);
        TextView txtShip = dialogBill.findViewById(R.id.text_bill_ship);

        TextView txtCustomer = dialogBill.findViewById(R.id.text_customer_name);

        assert mRes != null;
        txtTitle.setText(mRes.getRes_name());
        txtAddress.setText(mRes.getRes_address());
        txtPhone.setText(mRes.getRes_phone());
        txtDate.setText(bill.getBill_date());

        txtTotal.setText(Formatter.format(bill.getTotal_price()));
        txtCash.setText(Formatter.format(bill.getGuest_money()));
        txtChange.setText(Formatter.format(bill.getMoney_back()));
        txtDiscount.setText(String.format("-%s", Formatter.format(bill.getDiscount())));
        txtSurcharge.setText(String.format("+%s", Formatter.format(bill.getSurcharge())));
        txtShip.setText(String.format("+%s", Formatter.format(bill.getShip())));

        txtCustomer.setText(bill.getCustomer());

        RecyclerView recycler = dialogBill.findViewById(R.id.recycler_bill);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(dialogBill.getContext()));
        recycler.setAdapter(new MyRecyclerBillAdapter(dialogBill.getContext(), infoList));

        dialogBill.findViewById(R.id.btn_intent).setOnClickListener(v -> {
            showToast("In phiếu", Toast.LENGTH_SHORT);
            exitActivity();
            dialogBill.dismiss();
        });

        dialogBill.show();
    }

    private void exitActivity() {
        Progress progress = new Progress(this, mViewGroup, mView);
        progress.showProgress(true);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, SaleActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            progress.showProgress(false);
            startActivity(intent);
        }, 1000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle(this.getResources().getString(R.string.msg_notification));
                if (mPayment == 0) {
                    onBackPressed();
                }else {
                    builder.setMessage("Hóa đơn này đã thanh toán xong, nhấn OK để thoát.");
                    builder.setPositiveButton("OK", (dialog, id) -> exitActivity());
                }
                builder.show();

                return true;
            }

            case R.id.item_note:
                dialogNote("Ghi chú thêm...", 1);
                return true;

            case R.id.item_customer: {
                dialogNote(getString(R.string.msg_customer), 2);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogNote(String hint, int s) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.note_dialog);

        mEdtInput = dialog.findViewById(R.id.edit_note);
        mEdtInput.setHint(hint);
        mEdtInput.setText(s == 1 ? sNote : sCustomer);

        final InputMethodManager imm = (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mEdtInput.setOnFocusChangeListener((v1, isFocused) -> {
            if (isFocused) {
                Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        });

        Button btnOK = dialog.findViewById(R.id.btn_confirm);
        btnOK.setOnClickListener(v12 -> {
            if (mEdtInput.getText().toString().trim().isEmpty()) {
                mEdtInput.setError(this.getResources().getString(R.string.error_empty));
            }else {
                if (s == 1) {
                    sNote = mEdtInput.getText().toString().trim().isEmpty() ? "" : mEdtInput.getText().toString().trim();
                }else {
                    sCustomer = mEdtInput.getText().toString().trim().isEmpty() ? "" : mEdtInput.getText().toString().trim();
                }dialog.cancel();
            }
        });

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v13 -> dialog.cancel());
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sBill = null;
        sCustomer = "";
        sNote = "";
    }

    private void showToast(String str, int length) {
        Toast.makeText(PaymentActivity.this, str, length).show();
    }

    private void AnhXa() {
        mEditCurrentT = findViewById(R.id.edit_current_total);
        mEditDiscount = findViewById(R.id.edit_discount);
        mEditSurcharge = findViewById(R.id.edit_surcharge);
        mEditIntoMoney = findViewById(R.id.edit_into_money);
        mEditCash = findViewById(R.id.edit_cash);
        mEditChange = findViewById(R.id.edit_change);
        mEditChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (change < into_money) {
                    mEditChange.setTextColor(PaymentActivity.this.getResources().getColor(R.color.red));
                }else {
                    mEditChange.setTextColor(PaymentActivity.this.getResources().getColor(R.color.green));
                }
            }
        });
        mEditShip = findViewById(R.id.edit_ship);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_title_payment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getRestaurant(int idRes) {
        ApiRestaurant apiRestaurant = ApiClient.getClient().create(ApiRestaurant.class);
        Call<RestaurantResponse> call = apiRestaurant.getRestaurant(idRes);
        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantResponse> call, @NonNull Response<RestaurantResponse> response) {
                RestaurantResponse res = response.body();
                assert res != null;
                if (res.getSuccess()) {
                    mRes = res.getListRestaurant().get(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantResponse> call, @NonNull Throwable t) {
                Log.e("TAG - res", t.getMessage()) ;
            }
        });
    }
}
