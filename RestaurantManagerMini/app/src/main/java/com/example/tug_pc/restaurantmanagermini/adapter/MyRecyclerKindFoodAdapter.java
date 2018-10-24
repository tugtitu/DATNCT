package com.example.tug_pc.restaurantmanagermini.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.OrderActivity;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.KindFood;
import com.example.tug_pc.restaurantmanagermini.ultil.AnimItemMain;

import java.util.List;

public class MyRecyclerKindFoodAdapter extends RecyclerView.Adapter<MyRecyclerKindFoodAdapter.ViewHolder>
        implements ItemClickListener{
    private OrderActivity mContext;
    private List<KindFood> kindFoods;

    public MyRecyclerKindFoodAdapter(OrderActivity mContext, List<KindFood> kindFoods) {
        this.mContext = mContext;
        this.kindFoods = kindFoods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kind_food, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KindFood kindFood = kindFoods.get(position);

        holder.txtKFood.setText(kindFood.getKind_name());

        AnimItemMain.setAnimation(holder.itemView, position);
        AnimItemMain.setScaleAnimation(holder.itemView);

        holder.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return kindFoods.size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        if (!isLongClick) {
            int id = kindFoods.get(position).getId();

            if (id != 0) {
                mContext.getFood(id);
            } else {
                mContext.getFood(-1);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView txtKFood;

        private ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);
            txtKFood = itemView.findViewById(R.id.text_kind_food);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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
