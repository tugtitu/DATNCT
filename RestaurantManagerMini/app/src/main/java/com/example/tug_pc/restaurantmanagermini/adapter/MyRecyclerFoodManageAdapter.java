package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.FoodManageActivity;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiFood;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Food;
import com.example.tug_pc.restaurantmanagermini.model.KindFood;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultResponse;
import com.example.tug_pc.restaurantmanagermini.ultil.AnimItemMain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRecyclerFoodManageAdapter extends RecyclerView.Adapter<MyRecyclerFoodManageAdapter.ViewHolder>
        implements ItemClickListener{
    private static final DecimalFormat FORMATTER = new DecimalFormat("###,###,###");
    private FoodManageActivity mContext;
    private List<Food> mListFood;
    private List<KindFood> mListKindFood;

    public MyRecyclerFoodManageAdapter(FoodManageActivity context) {
        this.mContext = context;
        this.mListKindFood = new ArrayList<>();
        this.mListFood = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyRecyclerFoodManageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_food_manage, parent, false);
        return new ViewHolder(view);
    }

    public void addData(List<Food> product, List<KindFood> product2) {
        this.mListFood.clear();
        this.mListFood.addAll(product);

        this.mListKindFood.clear();
        this.mListKindFood.addAll(product2);
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerFoodManageAdapter.ViewHolder holder, int position) {
        if (mListFood.size() > 0) {
            Food food = mListFood.get(position);

            holder.txtFoodName.setText(food.getFood_name());
            holder.relativeLayout.setBackgroundResource(R.drawable.bg_item_food_manage);
            if (food.getId() != -1) {
                int promotions = food.getPromotions();
                int price = food.getPrice() - (food.getPrice() * promotions / 100);
                holder.text_price_promotions.setText(FORMATTER.format(price));
                if (food.getPromotions() != 0){
                    holder.text_promotions.setVisibility(View.VISIBLE);
                    holder.text_promotions.setText(String.valueOf(promotions) + "%");

                    holder.txtFoodPrice.setText(FORMATTER.format(food.getPrice()));
                }else {
                    holder.text_promotions.setVisibility(View.GONE);
                    holder.txtFoodPrice.setVisibility(View.GONE);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    params.weight = 2.0f;
                    holder.text_price_promotions.setLayoutParams(params);
                }
            }else {
                holder.txtFoodName.setTypeface(Typeface.DEFAULT_BOLD);
                holder.i_btn_delete.setVisibility(View.GONE);
                holder.txtFoodPrice.setVisibility(View.GONE);
                holder.text_price_promotions.setVisibility(View.GONE);
                holder.text_promotions.setVisibility(View.GONE);
            }

            AnimItemMain.setAnimation(holder.itemView, position);

            holder.setClickListener(this);
        }
    }


    @Override
    public int getItemCount() {
        return mListFood.size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        int is;
        Food food = mListFood.get(position);

        switch (view.getId()) {
            case R.id.image_btn_item: {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mContext);
                }
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle(mContext.getResources().getString(R.string.del));
                builder.setMessage(
                        mContext.getResources().getString(R.string.msg_are_you_sure) +" "+
                                mContext.getResources().getString(R.string.action_delete) + " ?"
                );
                builder.setPositiveButton("OK", (dialog, id) -> {
                    updateFood(null, 2, food.getId(), "", -1, -1, food.getKind_food(), food.getKind_food_name());
                });
                builder.setNegativeButton("Cancel", (dialog, id) -> {
                });
                builder.show();
                break;
            }

            default: {
                Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_custom_handling_data_food);
                Spinner spinner = dialog.findViewById(R.id.spinner_category_food);
                ArrayList<String> arrayList = convertString();
                ArrayAdapter<? extends String> adapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_spinner_item, arrayList);
                spinner.setAdapter(adapter);

                spinner.setSelection(arrayList.indexOf(food.getKind_food_name()));

                TextView text_title_dialog = dialog.findViewById(R.id.text_title_dialog);
                EditText text_view_name = dialog.findViewById(R.id.edit_input_name_food);
                EditText text_view_promotions = dialog.findViewById(R.id.edit_promotions);
                EditText text_view_price = dialog.findViewById(R.id.edit_price);

                if (food.getId() == -1) {
                    is = 0;
                    text_title_dialog.setText(R.string.msg_title_add_food);
                }else {
                    is = 1;
                    text_view_name.setText(food.getFood_name());
                    text_view_price.setText(String.valueOf(food.getPrice()));

                    long promotions = food.getPromotions();
                    if (promotions != 0) {
                        promotions = promotions * food.getPrice() / 100;
                    }
                    text_view_promotions.setText(String.valueOf(promotions));
                    text_title_dialog.setText(R.string.msg_title_edit_food);
                }

                dialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
                    int id = spinner.getSelectedItemPosition();
                    KindFood kindFood = mListKindFood.get(id);

                    String  name = "";
                    long  promotions = Long.parseLong(text_view_promotions.getText().toString().trim().isEmpty() ? "0" : text_view_promotions.getText().toString().trim());
                    long  price = Long.parseLong(text_view_price.getText().toString().trim().isEmpty() ? "0" : text_view_price.getText().toString().trim());
                    boolean isEmpty = false;

                    if (text_view_name.getText().toString().trim().length() == 0) {
                        text_view_name.setError(mContext.getString(R.string.error_empty));
                        isEmpty = true;
                    }else name = text_view_name.getText().toString().trim();
                    if (price < 1000) {
                        text_view_price.setError(mContext.getString(R.string.minimum));
                        isEmpty = true;
                    }else if (promotions > 0 && promotions < 1000) {
                        text_view_promotions.setError(mContext.getString(R.string.minimum));
                        isEmpty = true;
                    }else if (price <= promotions) {
                        Toast.makeText(mContext, "Khuyến mãi phải nhỏ hơn giá?", Toast.LENGTH_SHORT).show();
                        isEmpty = true;
                    }

                    if (!isEmpty) {
                        if (promotions != 0) {
                            promotions = promotions * 100 / price;
                        }
                        updateFood(dialog, is, food.getId(), name, promotions, price, kindFood.getId(), kindFood.getKind_name());
                    }
                });

                dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
                    dialog.cancel();
                });

                dialog.show();

                break;
            }
        }
    }

    private void updateFood(Dialog dialog, int is, int id, String name, long promotions, long price, int idKindFood, String kind_food_name) {
        ApiFood apiFood = ApiClient.getClient().create(ApiFood.class);
        Call<ResultResponse> call = apiFood.updateFood(is, id, name, promotions, price, idKindFood);
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResultResponse> call, @NonNull Response<ResultResponse> response) {
                ResultResponse result = response.body();
                assert result != null;
                switch (result.getResult()) {
                    case -1: {
                        dialogConfirm("Error!", "Đã có lỗi gì đó xảy ra!");
                        break;
                    }

                    case 0: {
                        dialogConfirm("Cảnh báo!", "Hãy chắc chắn rằng món này đang không được gọi!");
                        break;
                    }

                    case 1: {
                        mContext.makeToast("Cập nhật thành công!");
                        mContext.getFoodByCategory(idKindFood, kind_food_name);
                        break;
                    }

                    case 2: {
                        mContext.makeToast("Xóa thành công!");
                        mContext.getFoodByCategory(idKindFood, kind_food_name);
                        break;
                    }

                    case 3: {
                        mContext.makeToast("Thêm thành công!");
                        mContext.getFoodByCategory(idKindFood, kind_food_name);
                        break;
                    }
                }

                if (dialog != null) dialog.cancel();
            }

            @Override
            public void onFailure(@NonNull Call<ResultResponse> call, @NonNull Throwable t) {
                mContext.makeToast(t.getMessage());
            }
        });
    }

    private void dialogConfirm(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, id) -> { });
        builder.show();
    }

    private ArrayList<String> convertString() {
        ArrayList<String> array_spinner = new ArrayList<>();
        for (int i = 0; i < mListKindFood.size(); i++) {
            array_spinner.add(mListKindFood.get(i).getKind_name());
        }
        return array_spinner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView text_price_promotions, txtFoodName, txtFoodPrice, text_promotions;
        private ImageButton i_btn_delete;
        private HorizontalScrollView scroll;
        private RelativeLayout relativeLayout;

        private ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);
            text_price_promotions = itemView.findViewById(R.id.text_price_promotions);
            txtFoodName = itemView.findViewById(R.id.text_food_name);
            txtFoodPrice = itemView.findViewById(R.id.text_food_price);
            i_btn_delete = itemView.findViewById(R.id.image_btn_item);
            text_promotions = itemView.findViewById(R.id.text_promotions);

            scroll = itemView.findViewById(R.id.scroll_food_name);
            relativeLayout = itemView.findViewById(R.id.relative);

            itemView.setOnClickListener(this);
            i_btn_delete.setOnClickListener(this);
            text_price_promotions.setOnClickListener(this);
            txtFoodName.setOnClickListener(this);
            txtFoodPrice.setOnClickListener(this);
            text_promotions.setOnClickListener(this);
            scroll.setOnClickListener(this);
            itemView.findViewById(R.id.scroll_food_price).setOnClickListener(this);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }

        void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }
    }
}
