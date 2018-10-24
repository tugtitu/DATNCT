package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.KitchenActivity;
import com.example.tug_pc.restaurantmanagermini.avtivity.MainActivity;
import com.example.tug_pc.restaurantmanagermini.avtivity.ReportActivity;
import com.example.tug_pc.restaurantmanagermini.avtivity.SaleActivity;
import com.example.tug_pc.restaurantmanagermini.avtivity.SettingActivity;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Main;
import com.example.tug_pc.restaurantmanagermini.model.Restaurant;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.ultil.Progress;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerMainAdapter extends RecyclerView.Adapter<MyRecyclerMainAdapter.ViewHolder>
        implements ItemClickListener{
    private MainActivity context;
    private List<Main> mains;
    private Staff staff;
    private Restaurant res;
    private Progress progress;

    public MyRecyclerMainAdapter(MainActivity context, Staff staff, Restaurant restaurant) {
        this.context = context;
        this.staff = staff;
        this.res = restaurant;
        AddMainInfo();
    }

    @NonNull
    @Override
    public MyRecyclerMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_main, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerMainAdapter.ViewHolder holder, int position) {
        Main main = mains.get(position);

        holder.imgItemMain.setBackground(main.getImg());

        holder.txtItemMain.setText(main.getName() + "");

        holder.itemView.setTag(position);
        holder.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mains.size();
    }

    @Override
    public void onClick(View v, int position, boolean isLongClick) {
        if (!isLongClick) {
            LinearLayout mMainFormView = context.findViewById(R.id.main_form);
            ProgressBar mProgressView = context.findViewById(R.id.main_progress);
            progress = new Progress(context, mMainFormView, mProgressView);
            Intent intent = null;
            switch (mains.get(position).getId()){
                case 1:
                    intent = new Intent(context, SaleActivity.class);
                    break;
                case 2:
                    break;
                case 3:
                    intent =  new Intent(context, ReportActivity.class);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    intent =  new Intent(context, SettingActivity.class);
                    break;
                case 7:
                    intent =  new Intent(context, KitchenActivity.class);
                    break;
            }

            if (intent != null){
                intent.putExtra("staff", staff);
                intent.putExtra("res", res);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                progress.showProgress(true);
                Handler handler = new Handler();
                Intent finalIntent = intent;
                handler.postDelayed(() -> {
                    context.startActivity(finalIntent);
                    progress.showProgress(false);
                }, 1000);
            }
        }
    }

    private void AddMainInfo() {
        mains = new ArrayList<>();

        switch (staff.getKind_id()) {
            case 2: case 3: {
                mains.add(new Main(1, context.getResources().getString(R.string.item_sale), context.getResources().getDrawable(R.drawable.sale)));
//                mains.add(new Main(2, context.getResources().getString(R.string.item_quick_service), context.getResources().getDrawable(R.drawable.quick_service)));
                mains.add(new Main(3, context.getResources().getString(R.string.item_report), context.getResources().getDrawable(R.drawable.report)));
                break;
            }

            case 4: {
//                mains.add(new Main(4, context.getResources().getString(R.string.item_warehouse), context.getResources().getDrawable(R.drawable.warehouse)));
//                mains.add(new Main(5, context.getResources().getString(R.string.item_payment), context.getResources().getDrawable(R.drawable.payment)));
                break;
            }

            default: {
                mains.add(new Main(1, context.getResources().getString(R.string.item_sale), context.getResources().getDrawable(R.drawable.sale)));
                mains.add(new Main(3, context.getResources().getString(R.string.item_report), context.getResources().getDrawable(R.drawable.report)));
                mains.add(new Main(4, context.getResources().getString(R.string.item_warehouse), context.getResources().getDrawable(R.drawable.warehouse)));
                mains.add(new Main(5, context.getResources().getString(R.string.item_payment), context.getResources().getDrawable(R.drawable.payment)));
                mains.add(new Main(6, context.getResources().getString(R.string.item_setting), context.getResources().getDrawable(R.drawable.setting_65)));
                mains.add(new Main(7, context.getResources().getString(R.string.item_kitchen), context.getResources().getDrawable(R.drawable.setting_65)));
                break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView txtItemMain;
        private ImageView imgItemMain;

        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            txtItemMain = itemView.findViewById(R.id.text_item_main);
            imgItemMain = itemView.findViewById(R.id.image_item_main);

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
