package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.OrderActivity;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiBill;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.BillDetails;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.response.Result;
import com.example.tug_pc.restaurantmanagermini.ultil.AnimItemMain;
import com.example.tug_pc.restaurantmanagermini.ultil.Formatter;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tug_pc.restaurantmanagermini.avtivity.OrderActivity.sPublicBill;

public class MyRecyclerOrderAdapter extends RecyclerView.Adapter<MyRecyclerOrderAdapter.ViewHolder>
        implements ItemClickListener{
    private OrderActivity mContext;
    private List<BillDetails> billDetails;
    private int isDelete = -1;
    private Staff mStaff;

    public MyRecyclerOrderAdapter(OrderActivity context, List<BillDetails> billDetails, Staff sStaff) {
        this.mContext = context;
        this.billDetails = billDetails;
        this.mStaff = sStaff;
    }

    @NonNull
    @Override
    public MyRecyclerOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_order, parent, false);
        return new MyRecyclerOrderAdapter.ViewHolder(view);
    }

    public void addData(List<BillDetails> product) {
        this.billDetails.clear();
        this.billDetails.addAll(product);
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerOrderAdapter.ViewHolder holder, int position) {
        BillDetails detail = billDetails.get(position);

        if (!detail.getNote().isEmpty()) {
            holder.iBtnNote.setAlpha(1.0f);
        }else {
            holder.iBtnNote.setAlpha(0.3f);
        }

        if (isDelete != -1) {
            holder.iBtnItem.setVisibility(View.VISIBLE);
            if (isDelete == 1) {
                if (detail.getStatus() == 1) {
                    holder.iBtnItem.setImageResource(R.drawable.checked);
                }else {
                    holder.iBtnItem.setImageResource(R.drawable.uncheck);
                }
            }else {
                holder.iBtnItem.setImageResource(R.drawable.icons_delete);
            }
        }else {
            holder.iBtnItem.setVisibility(View.GONE);
        }

        holder.textOrderQuantity.setText(detail.getQuantity()+"");
        holder.textOrderNameFood.setText(detail.getFood_name());
        int promotions = detail.getPromotions();
        int price = detail.getPrice() - (detail.getPrice() * promotions / 100);
        holder.textPricePromotions.setText(Formatter.format(price));
        if (detail.getPromotions() != 0) {

            holder.textPromotions.setVisibility(View.VISIBLE);
            holder.textPromotions.setText(String.valueOf(promotions) + "%");

            holder.textPrice.setVisibility(View.VISIBLE);
            holder.textPrice.setText(Formatter.format(detail.getPrice()));
        }else {
            holder.textPromotions.setVisibility(View.GONE);
            holder.textPrice.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            params.weight = 2.0f;
            holder.textPricePromotions.setLayoutParams(params);
        }

        if (mStaff.get_id() != 3) {
            holder.iBtnMinus.setVisibility(View.VISIBLE);
            holder.iBtnPlus.setVisibility(View.VISIBLE);
        }else {
            holder.iBtnMinus.setVisibility(View.INVISIBLE);
            holder.iBtnPlus.setVisibility(View.INVISIBLE);
        }

        AnimItemMain.setAnimation(holder.itemView, position);

        holder.itemView.setTag(holder);
        holder.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return billDetails.size();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        if (isLongClick && mStaff.get_id() != 3){
            if (isDelete == -1) {
                MenuBuilder menuBuilder = new MenuBuilder(mContext);
                MenuInflater inflater = new MenuInflater(mContext);
                inflater.inflate(R.menu.menu_long_click_item_order, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(mContext, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);

                // Set Item Click Listener
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_delete_multiple: {
                                isDelete = 2;
                                notifyDataSetChanged();
                                return true;
                            }

                            case R.id.item_processing_finished: {
                                isDelete = 1;
                                notifyDataSetChanged();
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) { }
                });
                // Display the menu
                optionsMenu.show();
            }else {
                isDelete = -1;
                notifyDataSetChanged();
            }
        }else {
            BillDetails detail = billDetails.get(position);
            int fid = detail.getFood_id();

            switch (view.getId()) {
                case R.id.image_btn_plus: {
                    mContext.updateOrderInfo(sPublicBill.getId(), fid, detail.getId(), 1, "");
                    break;
                }

                case R.id.image_btn_minus: {
                    mContext.updateOrderInfo(sPublicBill.getId(), fid, detail.getId(), - 1, "");
                    break;
                }

                case R.id.image_btn_item: {
                    switch (isDelete) {
                        case 1: {
                            if (detail.getStatus() == 0) {
                                detail.setStatus(1);
                            }else {
                                detail.setStatus(0);
                            }

                            processingFinished(detail);
                            break;
                        }

                        case 2: {
                            mContext.updateOrderInfo(sPublicBill.getId(), fid, detail.getId(), 0, "");
                            break;
                        }

                        default: break;
                    }
                    break;
                }

                case R.id.image_btn_note: {
                    Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.note_dialog);
                    EditText edtNote = dialog.findViewById(R.id.edit_note);

                    edtNote.setText(detail.getNote());

                    final InputMethodManager imm = (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    edtNote.setOnFocusChangeListener((v1, isFocused) -> {
                        if (isFocused) {
                            Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                        }
                    });

                    Button btnOK = dialog.findViewById(R.id.btn_confirm);
                    btnOK.setOnClickListener(v12 -> {
                        String str = String.valueOf(edtNote.getText());
                        if (!str.isEmpty()) {
                            mContext.updateOrderInfo(sPublicBill.getId(), fid, detail.getId(), -9999, str);
                        }
                        dialog.cancel();
                    });

                    Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                    btnCancel.setOnClickListener(v13 -> {
                        edtNote.setText("");
                        dialog.cancel();
                    });
                    dialog.show();
                    break;
                }
            }
        }
    }

    private void processingFinished(BillDetails detail) {
        ApiBill apiBill = ApiClient.getClient().create(ApiBill.class);
        Call<Result> call = apiBill.processingFinished(detail.getId(), detail.getBill_id(), detail.getStatus());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                Result result = response.body();
                assert result != null;
                if (result.getSuccess()) {
                    notifyDataSetChanged();
                }else {
                    mContext.showToast(result.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                mContext.showToast(t.getMessage());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private ItemClickListener clickListener;
        private TextView textOrderQuantity, textOrderNameFood, textPricePromotions, textPrice, textPromotions;
        private ImageButton iBtnPlus, iBtnMinus, iBtnNote, iBtnItem;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            textOrderQuantity = itemView.findViewById(R.id.text_order_quantity);
            textOrderNameFood = itemView.findViewById(R.id.text_order_name_food);
            textPricePromotions = itemView.findViewById(R.id.text_price_promotion);
            textPrice = itemView.findViewById(R.id.text_price);
            textPromotions = itemView.findViewById(R.id.text_price_promotions);

            iBtnPlus = itemView.findViewById(R.id.image_btn_plus);
            iBtnMinus = itemView.findViewById(R.id.image_btn_minus);
            iBtnNote = itemView.findViewById(R.id.image_btn_note);
            iBtnItem = itemView.findViewById(R.id.image_btn_item);

            iBtnNote.setOnClickListener(this);
            iBtnNote.setOnLongClickListener(this);

            if (mStaff.get_id() != 3) {
                textOrderQuantity.setOnClickListener(this);
                textOrderNameFood.setOnClickListener(this);
                textPricePromotions.setOnClickListener(this);
                textPrice.setOnClickListener(this);
                textPromotions.setOnClickListener(this);
                iBtnPlus.setOnClickListener(this);
                iBtnMinus.setOnClickListener(this);
                iBtnItem.setOnClickListener(this);

                textOrderQuantity.setOnLongClickListener(this);
                textOrderNameFood.setOnLongClickListener(this);
                textPricePromotions.setOnLongClickListener(this);
                textPrice.setOnLongClickListener(this);
                textPromotions.setOnLongClickListener(this);
                iBtnPlus.setOnLongClickListener(this);
                iBtnMinus.setOnLongClickListener(this);
            }

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
