package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.adapter.MyRecyclerReportAdapter;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiBill;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiFood;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiKindFood;
import com.example.tug_pc.restaurantmanagermini.model.Bill;
import com.example.tug_pc.restaurantmanagermini.model.BillDetails;
import com.example.tug_pc.restaurantmanagermini.model.Food;
import com.example.tug_pc.restaurantmanagermini.model.KindFood;
import com.example.tug_pc.restaurantmanagermini.model.response.BillResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.FoodResponse;
import com.example.tug_pc.restaurantmanagermini.model.response.KindFoodResponse;
import com.example.tug_pc.restaurantmanagermini.ultil.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class ReportActivity extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private MyRecyclerReportAdapter mAdapter;
    private TextView mText_choose_from_day, mText_choose_to_day;
    private TextView mText_report_money;
    private TextView mText_quantity_food, mText_money_food;

    private Spinner mSpinner_kind_food, mSpinner_food;
    private List<KindFood> mKindFoods;
    private List<Food> mFoods;

    private ViewGroup mLinear_by_food, mRelative_by_day, mLinear_report_by_food;
    private int sl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        setSupportActionBar(findViewById(R.id.tool_title_report));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.item_report));

        RecyclerView relative_bill_report = findViewById(R.id.relative_bill_report);
        relative_bill_report.setHasFixedSize(true);
        relative_bill_report.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecyclerReportAdapter(this);
        relative_bill_report.setItemAnimator(new DefaultItemAnimator());
        relative_bill_report.setAdapter(mAdapter);

        mText_choose_from_day = findViewById(R.id.text_choose_from_day);
        mText_choose_to_day = findViewById(R.id.text_choose_to_day);
        mText_report_money = findViewById(R.id.text_report_money);
        mLinear_report_by_food = findViewById(R.id.linear_report_by_food);
        mRelative_by_day = findViewById(R.id.relative_by_day);
        mLinear_by_food = findViewById(R.id.linear_by_food);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        String text_time = " "+SIMPLE_DATE_FORMAT.format(calendar.getTime());
        mText_choose_from_day.setText(text_time);
        mText_choose_to_day.setText(text_time);

        mText_choose_from_day.setOnClickListener(v -> {
            try {
                chooseTime(mText_choose_from_day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        mText_choose_to_day.setOnClickListener(v -> {
            try {
                chooseTime(mText_choose_to_day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        getBillByDate();
    }

    public void getKindFood() {
        ApiKindFood apiKindFood = ApiClient.getClient().create(ApiKindFood.class);
        Call<KindFoodResponse> call = apiKindFood.getAllDataKindFood();
        call.enqueue(new Callback<KindFoodResponse>() {
            @Override
            public void onResponse(@NonNull Call<KindFoodResponse> call, @NonNull Response<KindFoodResponse> response) {
                KindFoodResponse kfResponse = response.body();
                assert kfResponse != null;
                if (kfResponse.getStatus() ==1) {
                    mKindFoods = kfResponse.getListKindFood();
                    setSelectedItemSpinner(mSpinner_kind_food, false);
                }else { showToast(kfResponse.getMessage()); }
            }

            @Override
            public void onFailure(@NonNull Call<KindFoodResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void setSelectedItemSpinner(Spinner spinner, boolean food) {
        ArrayList<String> array = convertString(food);
        ArrayAdapter<? extends String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, array);
        spinner.setAdapter(adapter);

        spinner.setSelection(array.indexOf(array.get(0)));
        if (!food) {
            int position = spinner.getSelectedItemPosition();
            KindFood kindFood = mKindFoods.get(position);
            getFoodByKindFood(kindFood.getId());
        }else {
            int position = spinner.getSelectedItemPosition();
            Food food1 = mFoods.get(position);
            getBillByFood(food1.getId());
        }
    }

    public void getFoodByKindFood(int id) {
        ApiFood apiFood = ApiClient.getClient().create(ApiFood.class);
        Call<FoodResponse> call = apiFood.getDataFood(id);
        call.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(@NonNull Call<FoodResponse> call, @NonNull Response<FoodResponse> response) {
                FoodResponse foodResponse = response.body();
                assert foodResponse != null;
                if (foodResponse.getStatus() == 1) {
                    mFoods = foodResponse.getListFood();
                    setSelectedItemSpinner(mSpinner_food, true);
                }else { showToast(foodResponse.getMessage()); }
            }

            @Override
            public void onFailure(@NonNull Call<FoodResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void chooseTime(TextView mText) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String txtTime = mText.getText().toString().trim();
        calendar.setTime(SIMPLE_DATE_FORMAT.parse(txtTime));

        int year = calendar.get(Calendar.YEAR);
        int mon = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, (view, year1, month, dayOfMonth) -> {
            calendar.set(year1, month, dayOfMonth);
            String time = " "+SIMPLE_DATE_FORMAT.format(calendar.getTime());
            if (!time.equals(txtTime)) {
                mText.setText(time);
                getBillByDate();
            }
        }, year, mon, day);
        pickerDialog.show();
    }

    private long getTotalPrice(List<Bill> bills) {
        long total = 0;
        for (int i = 0; i < bills.size(); i++) {
            total += bills.get(i).getTotal_price();
        }
        return total;
    }

    private long getTotalPriceByFood(List<Bill> bills, int id) {
        sl = 0;
        long total = 0;
        for (int i = 0; i < bills.size(); i++) {
            List<BillDetails> billDetails = bills.get(i).getListBillDetails();
            for (int j = 0; j < billDetails.size(); j++) {
                BillDetails details = billDetails.get(j);
                if (details.getFood_id() == id) {
                    sl += details.getQuantity();
                    total += (details.getPrice() - details.getPromotions() * details.getPrice() / 100) * details.getQuantity();
                }
            }
        }
        return total;
    }

    @SuppressLint("SimpleDateFormat")
    public void getBillByDate() {
        String from_time = mText_choose_from_day.getText().toString().trim()+" 00:00:00";
        String to_time = mText_choose_to_day.getText().toString().trim()+" 23:59:59";
        ApiBill apiBill = ApiClient.getClient().create(ApiBill.class);
        Call<BillResponse> call = apiBill.getBillByDate(from_time, to_time);
        call.enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(@NonNull Call<BillResponse> call, @NonNull Response<BillResponse> response) {
                List<Bill> mListBill;
                BillResponse billResponse = response.body();
                assert billResponse != null;
                if (billResponse.getSuccess()) {
                    mListBill = billResponse.getListBill();
                }else { mListBill = new ArrayList<>(); }
                mText_report_money.setText(Formatter.format(getTotalPrice(mListBill)));
                mAdapter.addData(mListBill);
            }

            @Override
            public void onFailure(@NonNull Call<BillResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    public void getBillByFood(int id) {
        ApiBill apiBill = ApiClient.getClient().create(ApiBill.class);
        Call<BillResponse> call = apiBill.getBillByFood(id);
        call.enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(@NonNull Call<BillResponse> call, @NonNull Response<BillResponse> response) {
                List<Bill> mListBill;
                BillResponse billResponse = response.body();
                assert billResponse != null;
                if (billResponse.getSuccess()) {
                    mListBill = billResponse.getListBill();
                }else { mListBill = new ArrayList<>(); }

                mText_report_money.setText(Formatter.format(getTotalPrice(mListBill)));
                mText_money_food.setText(Formatter.format(getTotalPriceByFood(mListBill, id)));
                mText_quantity_food.setText(String.valueOf(sl));
                mAdapter.addData(mListBill);
            }

            @Override
            public void onFailure(@NonNull Call<BillResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private ArrayList<String> convertString(boolean food) {
        ArrayList<String> array_spinner = new ArrayList<>();
        if (food) {
            for (int i = 0; i < mFoods.size(); i++) {
                array_spinner.add(mFoods.get(i).getFood_name());
            }
        }else {
            for (int i = 0; i < mKindFoods.size(); i++) {
                array_spinner.add(mKindFoods.get(i).getKind_name());
            }
        }
        return array_spinner;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_report_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.item_by_day: {
                mRelative_by_day.setVisibility(View.VISIBLE);
                mLinear_by_food.setVisibility(View.GONE);
                mLinear_report_by_food.setVisibility(View.GONE);
                onResume();
                return true;
            }

            case R.id.item_by_food: {
                mText_quantity_food = findViewById(R.id.text_quantity_food);
                mText_money_food = findViewById(R.id.text_money_food);
                mLinear_report_by_food.setVisibility(View.VISIBLE);
                mRelative_by_day.setVisibility(View.GONE);
                mLinear_by_food.setVisibility(View.VISIBLE);
                mKindFoods = new ArrayList<>();
                mFoods = new ArrayList<>();

                mSpinner_kind_food = findViewById(R.id.spinner_kind_food);
                mSpinner_kind_food.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        KindFood kindFood = mKindFoods.get(position);
                        getFoodByKindFood(kindFood.getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                mSpinner_food = findViewById(R.id.spinner_food);
                mSpinner_food.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Food food = mFoods.get(position);
                        getBillByFood(food.getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                getKindFood();
                return true;
            }
        }

        return false;
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
