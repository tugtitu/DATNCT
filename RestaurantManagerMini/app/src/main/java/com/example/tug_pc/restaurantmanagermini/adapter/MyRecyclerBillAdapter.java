package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.model.BillDetails;
import com.example.tug_pc.restaurantmanagermini.ultil.Formatter;

import java.util.List;

public class MyRecyclerBillAdapter extends RecyclerView.Adapter<MyRecyclerBillAdapter.ViewHolder> {
    private Context context;
    private List<BillDetails> billDetails;

    public MyRecyclerBillAdapter(Context context, List<BillDetails> billDetails) {
        this.context = context;
        this.billDetails = billDetails;
    }

    @NonNull
    @Override
    public MyRecyclerBillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_bill, parent, false);
        return new MyRecyclerBillAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerBillAdapter.ViewHolder holder, int position) {
        BillDetails orderIn = billDetails.get(position);
        holder.textQuantity.setText(orderIn.getQuantity() + "");
        holder.textNameFood.setText(orderIn.getFood_name());
        long total = (orderIn.getPrice() - (orderIn.getPrice() / 100 * orderIn.getPromotions())) * orderIn.getQuantity();
        holder.textTotal.setText(Formatter.format(total));
    }

    @Override
    public int getItemCount() {
        return billDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textQuantity, textNameFood, textTotal;

        ViewHolder(View itemView) {
            super(itemView);
            textQuantity = itemView.findViewById(R.id.text_quantity);
            textNameFood = itemView.findViewById(R.id.text_name_food);
            textTotal = itemView.findViewById(R.id.text_total);
        }
    }
}
