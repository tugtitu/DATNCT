package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.TableManageActivity;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiTable;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Area;
import com.example.tug_pc.restaurantmanagermini.model.Table;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultResponse;
import com.example.tug_pc.restaurantmanagermini.ultil.AnimItemMain;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRecyclerTableManageAdapter extends RecyclerView.Adapter<MyRecyclerTableManageAdapter.ViewHolder>
        implements ItemClickListener{
    private TableManageActivity mContext;
    private List<Table> mListTab;
    private List<Area> mListArea;

    public MyRecyclerTableManageAdapter(TableManageActivity context) {
        this.mContext = context;
        this.mListArea = new ArrayList<>();
        this.mListTab = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyRecyclerTableManageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_table_manage, parent, false);
        return new ViewHolder(view);
    }

    public void addData(List<Table> product, List<Area> product2) {
        this.mListTab.clear();
        this.mListTab.addAll(product);

        this.mListArea.clear();
        this.mListArea.addAll(product2);
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerTableManageAdapter.ViewHolder holder, int position) {
        if (mListTab.size() > 0) {
            Table table = mListTab.get(position);

            if (table.getId() != -1) {
                holder.text_table_name.setText(table.getName() + " - " + table.getArea_name());
                holder.image_delete_table.setVisibility(View.VISIBLE);
            }else {
                holder.image_table.setImageResource(R.drawable.add_to_favorites);
                holder.text_table_name.setText(table.getName());
                holder.image_delete_table.setVisibility(View.GONE);
            }

            holder.itemView.setTag(holder);
            holder.setClickListener(this);
            holder.image_delete_table.setOnClickListener(view -> {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mContext);
                }
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle(mContext.getResources().getString(R.string.del));
                builder.setMessage(
                        mContext.getResources().getString(R.string.msg_are_you_sure) +" "+
                                mContext.getResources().getString(R.string.action_delete) + " ?"
                );
                builder.setPositiveButton("OK", (dialog, id) -> {
                    updateTable(null, 2, table.getId(), table.getName(), table.getArea(), table.getArea_name());
                });
                builder.setNegativeButton("Cancel", (dialog, id) -> {
                });
                builder.show();
            });
        }

        AnimItemMain.setAnimation(holder.itemView, position);
    }


    @Override
    public int getItemCount() {
        return mListTab.size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        int is;
        Table table = mListTab.get(position);

        if (!isLongClick){
            Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_custom_handling_data_table);
            Spinner spinner = dialog.findViewById(R.id.spinner_category_table);
            ArrayList<String> arrayList = convertString();
            ArrayAdapter<? extends String> adapter = new ArrayAdapter<>(mContext,
                    android.R.layout.simple_spinner_item, arrayList);
            spinner.setAdapter(adapter);

            spinner.setSelection(arrayList.indexOf(table.getArea_name()));

            EditText text_view_name = dialog.findViewById(R.id.edit_input_name_table);
            TextView text_title_dialog = dialog.findViewById(R.id.text_title_dialog);

            if (table.getId() == -1) {
                is = 0;
                text_title_dialog.setText(R.string.msg_title_add_table);
            }else {
                is = 1;
                text_title_dialog.setText(R.string.msg_title_edit_table);
                text_view_name.setText(table.getName());
            }

            dialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
                int id = spinner.getSelectedItemPosition();
                Area area = mListArea.get(id);

                String  name = "";

                if (text_view_name.getText().toString().trim().length() == 0) {
                    text_view_name.setError(mContext.getString(R.string.error_field_required));
                }else {
                    name = text_view_name.getText().toString().trim();
                    updateTable(dialog, is, table.getId(), name, area.getId(), area.getName());
                }
            });

            dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
                dialog.cancel();
            });

            dialog.show();
        }
    }

    private void updateTable(Dialog dialog, int is, int id, String name, int idArea, String area) {
        ApiTable apiTable = ApiClient.getClient().create(ApiTable.class);
        Call<ResultResponse> call = apiTable.updateTable(is, id, name, idArea);
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResultResponse> call, @NonNull Response<ResultResponse> response) {
                ResultResponse result = response.body();
                assert result != null;
                switch (result.getResult()) {
                    case -1: {
                        dialogConfirm("Error!", "Đã có lỗi gì đó xảy ra!");
                        break;
                    }

                    case 0: {
                        dialogConfirm("Cảnh báo!", "Hãy chắc chắn rằng món này đang không được gọi!");
                        break;
                    }

                    case 1: {
                        mContext.makeToast("Cập nhật thành công!");
                        mContext.getTableByArea(idArea, area);
                        break;
                    }

                    case 2: {
                        mContext.makeToast("Xóa thành công!");
                        mContext.getTableByArea(idArea, area);
                        break;
                    }

                    case 3: {
                        mContext.makeToast("Thêm thành công!");
                        mContext.getTableByArea(idArea, area);
                        break;
                    }
                }

                if (dialog != null) dialog.cancel();
            }

            @Override
            public void onFailure(@NonNull Call<ResultResponse> call, @NonNull Throwable t) {
                mContext.makeToast(t.getMessage());
            }
        });
    }

    private void dialogConfirm(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, id) -> { });
        builder.show();
    }

    private ArrayList<String> convertString() {
        ArrayList<String> array_spinner = new ArrayList<>();
        for (int i = 0; i < mListArea.size(); i++) {
            array_spinner.add(mListArea.get(i).getName());
        }
        return array_spinner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView text_table_name;
        private ImageView image_table;
        private ImageButton image_delete_table;

        private ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            text_table_name = itemView.findViewById(R.id.text_table_name);
            text_table_name.setOnClickListener(this);
            text_table_name.setOnLongClickListener(this);

            image_table = itemView.findViewById(R.id.image_table);
            image_table.setOnClickListener(this);
            image_table.setOnLongClickListener(this);

            image_delete_table = itemView.findViewById(R.id.image_delete_table);
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
