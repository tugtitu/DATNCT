package com.example.tug_pc.restaurantmanagermini.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.R.layout;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;

import java.util.List;

public class MyRecyclerAddFastAdapter extends RecyclerView.Adapter<MyRecyclerAddFastAdapter.ViewHolder> {
    private Context context;
    private List<String> strings;

    public MyRecyclerAddFastAdapter(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @NonNull
    @Override
    public MyRecyclerAddFastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout.item_abacus, parent, false);
        return new MyRecyclerAddFastAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAddFastAdapter.ViewHolder holder, int position) {
        holder.txtAbacus.setText(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private ItemClickListener clickListener;
        private TextView txtAbacus;

        ViewHolder(View itemView) {
            super(itemView);
            txtAbacus = itemView.findViewById(R.id.text_abacus);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition(), false);
        }

        public void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }
    }
}
