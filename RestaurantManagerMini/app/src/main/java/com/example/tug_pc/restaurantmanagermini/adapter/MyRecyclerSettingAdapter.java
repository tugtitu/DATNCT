package com.example.tug_pc.restaurantmanagermini.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.FoodManageActivity;
import com.example.tug_pc.restaurantmanagermini.avtivity.SettingActivity;
import com.example.tug_pc.restaurantmanagermini.avtivity.StaffManageActivity;
import com.example.tug_pc.restaurantmanagermini.avtivity.TableManageActivity;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.GeneralSetting;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerSettingAdapter extends RecyclerView.Adapter<MyRecyclerSettingAdapter.ViewHolder>
        implements ItemClickListener {
    private SettingActivity mContext;
    private List<GeneralSetting> mList;

    public MyRecyclerSettingAdapter(SettingActivity context) {
        this.mContext = context;
        addResourcesList();
    }

    @NonNull
    @Override
    public MyRecyclerSettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_general, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerSettingAdapter.ViewHolder holder, int position) {
        GeneralSetting generalSetting = mList.get(position);

        holder.imgItemGeneral.setBackground(generalSetting.getIcon());
        holder.txtItemGeneral.setText(generalSetting.getName());
        holder.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Intent intent = null;
        GeneralSetting general = mList.get(position);
        switch (general.getId()) {
            case 1: {
                intent = new Intent(mContext, FoodManageActivity.class);
                break;
            }

            case 2: {
                intent = new Intent(mContext, StaffManageActivity.class);
                intent.putExtra("staff", mContext.getIntent().getSerializableExtra("staff"));
                break;
            }

            case 3: {
                intent = new Intent(mContext, TableManageActivity.class);
                break;
            }
        }
        assert intent != null;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    private void addResourcesList() {
        mList = new ArrayList<>();
        mList.add(new GeneralSetting(1, mContext.getResources().getDrawable(R.drawable.food_and_wine), mContext.getResources().getString(R.string.action_menu)));
        mList.add(new GeneralSetting(2, mContext.getResources().getDrawable(R.drawable.staff), mContext.getResources().getString(R.string.action_staff)));
        mList.add(new GeneralSetting(3, mContext.getResources().getDrawable(R.drawable.table), mContext.getString(R.string.action_table)));

    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private ItemClickListener onClickListener;
        private ImageView imgItemGeneral;
        private TextView txtItemGeneral;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            imgItemGeneral = itemView.findViewById(R.id.image_item_general);
            txtItemGeneral = itemView.findViewById(R.id.text_item_general);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v, getAdapterPosition(), false);
        }

        public void setOnClickListener(ItemClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }
    }
}
