package com.example.tug_pc.restaurantmanagermini.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.avtivity.StaffManageActivity;
import com.example.tug_pc.restaurantmanagermini.avtivity.UpdateInformationStaffActivity;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiStaff;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRecyclerStaffManageAdapter extends RecyclerView.Adapter<MyRecyclerStaffManageAdapter.ViewHolder> implements ItemClickListener {
    private StaffManageActivity mContext;
    private List<Staff> mListStaff;


    public MyRecyclerStaffManageAdapter(StaffManageActivity mContext) {
        this.mContext = mContext;
        this.mListStaff = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyRecyclerStaffManageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_staff, parent, false);
        return new MyRecyclerStaffManageAdapter.ViewHolder(view);
    }

    public void addData(List<Staff> product) {
        this.mListStaff.clear();
        this.mListStaff.addAll(product);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerStaffManageAdapter.ViewHolder holder, int position) {
        Staff staff = mListStaff.get(position);

        holder.text_view_name.setText(staff.getName());
        if (staff.get_id() != -1) {
            holder.iBtn_del.setVisibility(View.VISIBLE);
            holder.text_view_address.setVisibility(View.VISIBLE);
            holder.text_view_phone.setVisibility(View.VISIBLE);
            holder.text_view_address.setText(staff.getAddress());
            holder.text_view_phone.setText(staff.getPhone());
        }else {
            holder.iBtn_del.setVisibility(View.GONE);
            holder.text_view_address.setVisibility(View.GONE);
            holder.text_view_phone.setVisibility(View.GONE);
        }

        holder.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mListStaff.size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Staff staff = mListStaff.get(position);
        switch (view.getId()) {
            case R.id.image_btn_delete: {
                displayAlertDialog(staff);
                break;
            }

            default: {
                int id = mListStaff.get(position).get_id();
                Bundle bundle = new Bundle();
                bundle.putSerializable("staff", mListStaff.get(position));
                bundle.putBoolean("isEdit", id != -1);
                mContext.startActivity(
                        new Intent(mContext, UpdateInformationStaffActivity.class)
                                .putExtra("bundle_staff", bundle)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            }
        }
    }

    private void displayAlertDialog(Staff staff) {
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
            deleteStaff(staff.get_id(), staff.getStaff_root());
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> {
        });
        builder.show();
    }

    private void deleteStaff(int id_child, int id_rood) {
        ApiStaff apiStaff = ApiClient.getClient().create(ApiStaff.class);
        Call<ResultResponse> call =
                apiStaff.deleteStaff(id_child);

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
                        dialogConfirm("Cảnh báo!", "Nhân viên này vẫn đang hoạt động, không thể xóa!");
                        break;
                    }

                    case 1: {
                        mContext.showToast("Lưu thành công!");
                        mContext.getStaffChild(id_rood);
                        break;
                    }

                    case 2: {
                        mContext.showToast("Xóa thành công!");
                        mContext.getStaffChild(id_rood);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultResponse> call, @NonNull Throwable t) {
                mContext.showToast(t.getMessage());
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


    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private ItemClickListener clickListener;
        private TextView text_view_name, text_view_address, text_view_phone;
        private ImageButton iBtn_del;

        ViewHolder(View itemView) {
            super(itemView);
            text_view_name = itemView.findViewById(R.id.text_staff_child_name);
            text_view_address = itemView.findViewById(R.id.text_staff_child_address);
            text_view_phone = itemView.findViewById(R.id.text_staff_child_phone);
            iBtn_del = itemView.findViewById(R.id.image_btn_delete);

            text_view_name.setOnClickListener(this);
            text_view_address.setOnClickListener(this);
            text_view_phone.setOnClickListener(this);
            iBtn_del.setOnClickListener(this);
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
