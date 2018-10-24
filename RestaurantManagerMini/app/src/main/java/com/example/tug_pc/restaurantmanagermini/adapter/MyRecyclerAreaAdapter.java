package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.SaleActivity;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Area;
import com.example.tug_pc.restaurantmanagermini.ultil.AnimItemMain;

import java.io.Serializable;
import java.util.List;

public class MyRecyclerAreaAdapter extends RecyclerView.Adapter<MyRecyclerAreaAdapter.ViewHolder>
        implements ItemClickListener, Serializable {
    private SaleActivity context;
    private List<Area> areas;

    public MyRecyclerAreaAdapter(SaleActivity context, List<Area> areas) {
        this.context = context;
        this.areas = areas;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Area area = areas.get(position);
        holder.txtArea.setText(area.getName());

        setButtonBackground(holder, position);

        holder.setClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    private void setButtonBackground(ViewHolder holder, int position) {
        int ab = position;
        while (ab > 4){
            ab /= 2;
        }

        switch (ab) {
            case 2:
                holder.txtArea.setBackgroundResource(R.drawable.custom_button_press_effect);
                holder.txtArea.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case 3:
                holder.txtArea.setBackgroundResource(R.drawable.custom_button_press_effect2);
                holder.txtArea.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case 4:
                holder.txtArea.setBackgroundResource(R.drawable.custom_button_press_effect3);
                holder.txtArea.setTextColor(context.getResources().getColor(R.color.white));
                break;

            default:
                holder.txtArea.setBackgroundResource(R.drawable.custom_button_press_effect4);
                holder.txtArea.setTextColor(R.color.black);
                break;
        }

        AnimItemMain.setAnimation(holder.itemView, position);
//        AnimItemMain.setScaleAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        if (!isLongClick) {
            int id = areas.get(position).getId();

            context.getTableByAreaId(id);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView txtArea;

        private ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);
            txtArea = itemView.findViewById(R.id.text_areaT);
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
