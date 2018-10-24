package com.example.tug_pc.restaurantmanagermini.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.R.layout;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.BillDetails;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerKitchenDetailAdapter extends RecyclerView.Adapter<MyRecyclerKitchenDetailAdapter.ViewHolder>
        implements ItemClickListener{
    private Context mContext;
    private List<BillDetails> mBillDetails;
    public boolean isEnable = true;

    public MyRecyclerKitchenDetailAdapter(Context context) {
        this.mContext = context;
        this.mBillDetails = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyRecyclerKitchenDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layout.item_info_kitchen, parent, false);
        return new ViewHolder(view);
    }

    public void addData(List<BillDetails> product) {
        this.mBillDetails.clear();
        this.mBillDetails.addAll(product);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerKitchenDetailAdapter.ViewHolder holder, int position) {
        BillDetails info = mBillDetails.get(position);

        if (info != null) {
            holder.txtFoodName.setText(info.getFood_name());
            holder.txtQuantity.setText(String.valueOf(info.getQuantity()));
            holder.setClickListener(this);
            if (!isEnable) {
                holder.btnTraMonInfo.setVisibility(View.GONE);
            }else {
                holder.btnTraMonInfo.setVisibility(View.VISIBLE);
            }
        }

        holder.itemView.setTag(holder);
        holder.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mBillDetails.size();
    }


    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (!isLongClick){
            switch (view.getId()){
                case R.id.ckb_processing: {

                    break;
                }

                case R.id.btn_tra_mon_child: {
                    if (!holder.ckbProcessing.isChecked()) {
                        //aler
                        Toast.makeText(mContext, "Món chưa chế biến xong xác nhận gửi", Toast.LENGTH_SHORT).show();
                    }
                    //update info status bật thông báo cho nv biết
                    break;
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private ItemClickListener clickListener;
        private TextView txtFoodName, txtQuantity;
        TextView btnTraMonInfo;
        private CheckBox ckbProcessing;

        ViewHolder(View itemView) {
            super(itemView);

            txtFoodName = itemView.findViewById(R.id.text_food_name);
            txtQuantity = itemView.findViewById(R.id.text_quantity);
            btnTraMonInfo = itemView.findViewById(R.id.btn_tra_mon_child);
            ckbProcessing = itemView.findViewById(R.id.ckb_processing);

            ckbProcessing.setOnClickListener(this);
            btnTraMonInfo.setOnClickListener(this);
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
