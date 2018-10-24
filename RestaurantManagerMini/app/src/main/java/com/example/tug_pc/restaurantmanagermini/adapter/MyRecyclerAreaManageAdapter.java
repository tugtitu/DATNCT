package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.TableManageActivity;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiArea;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Area;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultResponse;
import com.example.tug_pc.restaurantmanagermini.ultil.AnimItemMain;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRecyclerAreaManageAdapter extends RecyclerView.Adapter<MyRecyclerAreaManageAdapter.ViewHolder>
        implements ItemClickListener{
    private TableManageActivity mContext;
    private List<Area> mListArea;
    private int is = 0;

    public MyRecyclerAreaManageAdapter(TableManageActivity mContext) {
        this.mContext = mContext;
        mListArea = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_manage, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mListArea.size() > 0) {
            Area area = mListArea.get(position);

            holder.text_areaT.setText(area.getName());

            AnimItemMain.setAnimation(holder.itemView, position);
            AnimItemMain.setScaleAnimation(holder.itemView);

            holder.setClickListener(this);
        }
    }

    public void addData(List<Area> product) {
        this.mListArea.clear();
        this.mListArea.addAll(product);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mListArea.size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        int id = mListArea.get(position).getId();
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_handling_data_kind_food);

        EditText text_view_name = dialog.findViewById(R.id.edit_input_name_kind_food);
        TextView text_title_dialog = dialog.findViewById(R.id.text_title_dialog);

        if (id == -1) {
            is = 0;
            text_title_dialog.setText(R.string.msg_title_add_area);
        }else {
            is = 1;
            text_title_dialog.setText(R.string.msg_title_edit_area);
        }

        if (!isLongClick) {
            if (id != -1) {
                mContext.getTableByArea(id, mListArea.get(position).getName());
            }else {
                is = 0;
                displayDialog(dialog, text_view_name, id);
            }
        }else {
            String name =  mListArea.get(position).getName();
            displayMenuBuilder(dialog, view, text_view_name, name, id);
        }
    }

    private void displayDialog(Dialog dialog, EditText text_view_name, int id) {
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            String  name = text_view_name.getText().toString().trim();

            if (text_view_name.getText().toString().trim().length() == 0) {
                text_view_name.setError(mContext.getString(R.string.error_empty));
            }else updateArea(dialog, is, id, name);
        });

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }

    @SuppressLint("RestrictedApi")
    private void displayMenuBuilder(Dialog dialog, View view, EditText edit, String name, int idCategory) {
        MenuBuilder menuBuilder = new MenuBuilder(mContext);
        MenuInflater inflater = new MenuInflater(mContext);
        inflater.inflate(R.menu.item_long_click_kind_food_manage, menuBuilder);
        MenuPopupHelper optionsMenu = new MenuPopupHelper(mContext, menuBuilder, view);
        optionsMenu.setForceShowIcon(true);

        // Set Item Click Listener
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                if (item.getItemId() == R.id.item_edit){
                    is = 1;
                    edit.setText(name);
                    displayDialog(dialog, edit, idCategory);
                    return true;
                }else {
                    is = 2;
                    displayAlertDialog(dialog, name, idCategory);
                    return true;
                }
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {}
        });
        // Display the menu
        optionsMenu.show();
    }

    private void displayAlertDialog(Dialog dia, String name, int idCategory) {
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
            updateArea(dia, is, idCategory, name);
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> {
        });
        builder.show();
    }

    private void updateArea(Dialog dialog, int is, int id, String name) {
        ApiArea apiArea = ApiClient.getClient().create(ApiArea.class);
        Call<ResultResponse> call = apiArea.updateArea(is, id, name);
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
                        dialogConfirm("Cảnh báo!", "Hãy chắc chắn rằng bàn này đang trống!");
                        break;
                    }

                    case 1: {
                        mContext.makeToast("Cập nhật thành công!");
                        mContext.displayTable();
                        break;
                    }

                    case 2: {
                        mContext.makeToast("Xóa thành công!");
                        mContext.displayTable();
                        break;
                    }

                    case 3: {
                        mContext.makeToast("Thêm thành công!");
                        mContext.displayTable();
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

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView text_areaT;

        private ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);
            text_areaT = itemView.findViewById(R.id.text_areaT);

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
