package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.ReportActivity;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Bill;
import com.example.tug_pc.restaurantmanagermini.ultil.AnimItemMain;
import com.example.tug_pc.restaurantmanagermini.ultil.Formatter;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.tug_pc.restaurantmanagermini.avtivity.ReportActivity.SIMPLE_DATE_FORMAT;

public class MyRecyclerReportAdapter extends RecyclerView.Adapter<MyRecyclerReportAdapter.ViewHolder>
        implements ItemClickListener, Serializable {
    private ReportActivity mContext;
    private List<Bill> mListBill;

    public MyRecyclerReportAdapter(ReportActivity context) {
        this.mContext = context;
        this.mListBill = new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new ViewHolder(v);
    }

    public void addData(List<Bill> product) {
        if (mListBill.size() > 0) {
            mListBill.clear();
        }
        mListBill.addAll(product);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = mListBill.get(position);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(SIMPLE_DATE_FORMAT.parse(bill.getBill_date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mText_bill_date.setText(SIMPLE_DATE_FORMAT.format(calendar.getTime()));

        long amount = bill.getTotal_price();
        holder.mText_total_price.setText(Formatter.format(amount));

        AnimItemMain.setAnimation(holder.itemView, position);
        holder.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mListBill.size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        if (!isLongClick) {
            Bill bill = mListBill.get(position);
            showBillDialog(bill);
        }
    }

    @SuppressLint("SetTextI18n")
    private void showBillDialog(Bill bill) {
        Dialog dialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_custom_show_bill);
        dialog.setCanceledOnTouchOutside(false);

        TextView mText_bill_date, mText_customer, mText_billing_staff;
        RecyclerView mRecycler_bill_order;
        TextView mText_bill_total_price, mText_bill_cash, mText_bill_change, mText_bill_discount, mText_bill_surcharge, mText_bill_ship;

        //findViewById
        mText_bill_date = dialog.findViewById(R.id.text_bill_date);
        mText_customer = dialog.findViewById(R.id.text_customer);
        mText_billing_staff = dialog.findViewById(R.id.text_billing_staff);
        mRecycler_bill_order = dialog.findViewById(R.id.recycler_bill_order);
        mText_bill_total_price = dialog.findViewById(R.id.text_bill_total_price);
        mText_bill_cash = dialog.findViewById(R.id.text_bill_cash);
        mText_bill_change = dialog.findViewById(R.id.text_bill_change);
        mText_bill_discount = dialog.findViewById(R.id.text_bill_discount);
        mText_bill_surcharge = dialog.findViewById(R.id.text_bill_surcharge);
        mText_bill_ship = dialog.findViewById(R.id.text_bill_ship);

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(SIMPLE_DATE_FORMAT.parse(bill.getBill_date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mText_bill_date.setText(mContext.getResources().getString(R.string.msg_date)+" "+SIMPLE_DATE_FORMAT.format(calendar.getTime()));
        mText_customer.setText((mContext.getResources().getString(R.string.msg_customer)+": "+bill.getCustomer()));
        mText_billing_staff.setText(mContext.getResources().getString(R.string.msg_billing_staff)+" "+ bill.getStaff_name());

        mRecycler_bill_order.setHasFixedSize(true);
        mRecycler_bill_order.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler_bill_order.setItemAnimator(new DefaultItemAnimator());
        mRecycler_bill_order.setAdapter(new MyRecyclerBillAdapter(mContext, bill.getListBillDetails()));

        mText_bill_total_price.setText(Formatter.format(bill.getTotal_price()));
        mText_bill_cash.setText(Formatter.format(bill.getGuest_money()));
        mText_bill_change.setText(Formatter.format(bill.getMoney_back()));
        mText_bill_discount.setText(String.format("-%s", Formatter.format(bill.getDiscount())));
        mText_bill_surcharge.setText(String.format("+%s", Formatter.format(bill.getSurcharge())));
        mText_bill_ship.setText(String.format("+%s", Formatter.format(bill.getShip())));

        dialog.findViewById(R.id.floating_ac_btn_exit).setOnClickListener(v -> dialog.cancel());

        dialog.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView mText_bill_date, mText_total_price;

        private ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);
            mText_bill_date = itemView.findViewById(R.id.text_bill_date);
            mText_total_price = itemView.findViewById(R.id.text_total_price);

            itemView.setOnClickListener(this);
            mText_bill_date.setOnClickListener(this);
            mText_total_price.setOnClickListener(this);
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
