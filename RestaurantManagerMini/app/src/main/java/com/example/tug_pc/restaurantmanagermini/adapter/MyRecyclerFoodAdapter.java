package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.OrderActivity;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Bill;
import com.example.tug_pc.restaurantmanagermini.model.Food;
import com.example.tug_pc.restaurantmanagermini.ultil.AnimItemMain;

import java.util.List;

import static com.example.tug_pc.restaurantmanagermini.avtivity.OrderActivity.sPublicBill;

public class MyRecyclerFoodAdapter extends RecyclerView.Adapter<MyRecyclerFoodAdapter.ViewHolder>
        implements ItemClickListener{
    private OrderActivity context;
    private List<Food> foods;

    public MyRecyclerFoodAdapter(OrderActivity context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public MyRecyclerFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    public void addData(List<Food> product) {
        this.foods.clear();
        this.foods.addAll(product);
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerFoodAdapter.ViewHolder holder, int position) {
        Food food = foods.get(position);

        holder.txtFoodName.setText(food.getFood_name());
        if (food.getId() != -1) {
            holder.txtFoodPrice.setText(food.getPrice()/1000+".");
            if (food.getPromotions() != 0){
                holder.txtPromotions.setText(food.getPromotions()+"%");
                holder.txtPromotions.setTextColor(context.getResources().getColor(R.color.orange));
            }else {
                holder.txtPromotions.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 2.0f;
                holder.txtFoodPrice.setLayoutParams(params);
            }
        }else {
            holder.linear_item_food.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 10);

            holder.txtFoodName.setLayoutParams(params);
            holder.txtFoodName.setGravity(Gravity.CENTER);
        }

        AnimItemMain.setAnimation(holder.itemView, position);

        holder.setClickListener(this);
    }


    @Override
    public int getItemCount() {
        return foods.size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        if (!isLongClick){
            Bill bill = sPublicBill;
            Food food = foods.get(position);

            //dialog
            if (bill != null) {
                context.updateOrderInfo(bill.getId(), food.getId(), -1, 1, "");
            }else {
                context.updateOrderInfo(-1, food.getId(), -1, 1, "");
            }
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView txtPromotions, txtFoodName, txtFoodPrice;
        private LinearLayout linear_item_food;

        private ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtPromotions = itemView.findViewById(R.id.text_price_promotions);
            txtFoodName = itemView.findViewById(R.id.text_food_name);
            txtFoodPrice = itemView.findViewById(R.id.text_food_price);

            linear_item_food = itemView.findViewById(R.id.linear_item_food);
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
