package com.example.tug_pc.restaurantmanagermini.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.KitchenActivity;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiTable;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Bill;
import com.example.tug_pc.restaurantmanagermini.model.BillDetails;
import com.example.tug_pc.restaurantmanagermini.model.Table;
import com.example.tug_pc.restaurantmanagermini.model.response.TableResponse;
import com.example.tug_pc.restaurantmanagermini.ultil.CustomLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRecyclerKitchenOrderAdapter extends RecyclerView.Adapter<MyRecyclerKitchenOrderAdapter.ViewHolder>
        implements ItemClickListener{
    private KitchenActivity mContext;
    private List<Bill> mOrders;
    private ArrayList<Integer> status_item;

    public MyRecyclerKitchenOrderAdapter(KitchenActivity context, List<Bill> orders) {
        this.mContext = context;
        this.mOrders = orders;
        mOrders = new ArrayList<>();
        status_item = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyRecyclerKitchenOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_order_kitchen, parent, false);
        return new MyRecyclerKitchenOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerKitchenOrderAdapter.ViewHolder holder, int position) {
        Bill order = mOrders.get(position);

        getTableById(holder, order.getTable_id());

        holder.itemView.setTag(holder);
        holder.setClickListener(this);
    }

    public void addData(List<Bill> product) {
        this.mOrders.clear();
        this.mOrders.addAll(product);
        notifyDataSetChanged();
    }

    private void getTableById(ViewHolder holder, int idT){
        ApiTable apiTable = ApiClient.getClient().create(ApiTable.class);
        Call<TableResponse> call = apiTable.getTableById(idT);
        call.enqueue(new Callback<TableResponse>() {
            @Override
            public void onResponse(@NonNull Call<TableResponse> call, @NonNull Response<TableResponse> response) {
                TableResponse tableResponse = response.body();
                assert tableResponse != null;
                if (tableResponse.getSuccess()) {
                    Table table = tableResponse.getListTable().get(0);
                    String txt = "Bàn: " + table.getName() + " - " + (table.getArea_name().isEmpty() ? "???" : table.getArea_name());
                    holder.text_table_name.setText(txt);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TableResponse> call, @NonNull Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        //update status order and table hiện thông báo lên màng hình cho nv!
        Bill order = mOrders.get(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (!isLongClick) {
            switch (view.getId()) {
                case R.id.btn_tra_mon: {

                    break;
                }

                default: {
                    MyRecyclerKitchenDetailAdapter adapter = new MyRecyclerKitchenDetailAdapter(mContext);
                    holder.recycler.setLayoutManager(new CustomLinearLayoutManager(mContext));
                    holder.recycler.setItemAnimator(new DefaultItemAnimator());
                    holder.recycler.setAdapter(adapter);
                    List<BillDetails> details = order.getListBillDetails();
                    for (int i = 0; i < status_item.size(); i++) {
                        if (position == status_item.get(i)) {
                            details = new ArrayList<>();
                            status_item.remove(i);
                            break;
                        }
                    }

                    if (details != null) {
                        status_item.add(position);
                    }
                    adapter.addData(details);
                    break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private ItemClickListener clickListener;
        private TextView text_table_name, text_table_time;
        private RecyclerView recycler;
        private Button btnTraMon;

        ViewHolder(View itemView) {
            super(itemView);
            text_table_name = itemView.findViewById(R.id.text_table_name);
            text_table_time = itemView.findViewById(R.id.text_table_date);
            btnTraMon = itemView.findViewById(R.id.btn_tra_mon);

            recycler = itemView.findViewById(R.id.recycler_order_detail);

            itemView.setOnClickListener(this);
            text_table_name.setOnClickListener(this);
            text_table_time.setOnClickListener(this);
            btnTraMon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition(), false);
        }

        void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }
    }
}
