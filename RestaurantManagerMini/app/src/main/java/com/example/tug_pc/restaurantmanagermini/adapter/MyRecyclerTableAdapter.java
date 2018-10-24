package com.example.tug_pc.restaurantmanagermini.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.R.string;
import com.example.tug_pc.restaurantmanagermini.avtivity.OrderActivity;
import com.example.tug_pc.restaurantmanagermini.avtivity.SaleActivity;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiTable;
import com.example.tug_pc.restaurantmanagermini.event.ItemClickListener;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.Table;
import com.example.tug_pc.restaurantmanagermini.model.response.Result;
import com.example.tug_pc.restaurantmanagermini.ultil.AnimItemMain;
import com.example.tug_pc.restaurantmanagermini.ultil.Progress;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tug_pc.restaurantmanagermini.avtivity.SaleActivity.isArea;

public class MyRecyclerTableAdapter extends RecyclerView.Adapter<MyRecyclerTableAdapter.ViewHolder>
        implements ItemClickListener, Serializable {
    private SaleActivity mContext;
    private List<Table> mTables;
    private Staff staff;
    private int isNeedPosition = -1;
    private int isManipulation = -1;
    private int idTa = -1;
    private Progress progress;
    private boolean mIsBooking = false;
    private static final String[] STRINGS = {
            "086", "096", "097", "098", "0162", "0163", "0164", "0165", "0166", "0167", "0168", "0169",
            "090", "093", "0120", "0121", "0122", "0126", "0128", "08966",
            "091", "094", "0123", "0124", "0125", "0127", "0129",
            "092", "0188", "0186",
            "099", "0199"};

    public MyRecyclerTableAdapter(SaleActivity mContext, List<Table> mTables, Staff staff) {
        this.mContext = mContext;
        this.mTables = mTables;
        this.staff = staff;
    }

    @NonNull
    @Override
    public MyRecyclerTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_table, parent, false);
        return new MyRecyclerTableAdapter.ViewHolder(view);
    }

    public void addData(List<Table> product) {
        this.mTables.clear();
        this.mTables.addAll(product);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerTableAdapter.ViewHolder holder, int position) {
        Table table = mTables.get(position);

        if (table.getId() == idTa) {
            holder.iBtnCancelOperation.setVisibility(View.VISIBLE);
            holder.txtTable.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.txtTable.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        }else {
            holder.iBtnCancelOperation.setVisibility(View.GONE);
            holder.txtTable.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.txtTable.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

        if (table.getStatus() == 0) {
            holder.txtTime.setVisibility(View.INVISIBLE);
            holder.imgTable.setImageResource(R.drawable.table_48_no);
            holder.frameGroup.setBackgroundResource(R.drawable.layout_bg);
        }else {
            int sec = 1000;
            String time = table.getTime();
            Handler handler = new Handler();
            holder.txtTime.setVisibility(View.VISIBLE);

            switch (table.getStatus()) {
                case 1: {
                    holder.imgTable.setImageResource(R.drawable.meal_48);
                    holder.frameGroup.setBackgroundResource(R.drawable.layout_bg_status1);
                    break;
                }

                case 2: {
                    sec = 60000;
                    time = table.getTime_booking();
                    holder.imgTable.setImageResource(R.drawable.table_48_no);
                    holder.frameGroup.setBackgroundResource(R.drawable.layout_bg_status2);
                    break;
                }

                case 3: {
                    holder.imgTable.setImageResource(R.drawable.are_processing_48);
                    holder.frameGroup.setBackgroundResource(R.drawable.layout_bg_status3);
                    break;
                }
            }

            handler.postDelayed(
                    new CountDownRunner(
                            handler,
                            holder.txtTime,
                            time,
                            sec
                    ), 1000);

            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_scroll_right);
            holder.txtTime.startAnimation(animation);
        }

        switch (table.getStatus()){
            case 0: {

                break;
            }

        }

        String txt = "Bàn: " + table.getName() + " - " + table.getArea_name();
        holder.txtTable.setText(txt);

        AnimItemMain.setFadeAnimation(holder.itemView);

        holder.itemView.setTag(holder);
        holder.setItemClickListener(this);
        holder.iBtnCancelOperation.setOnClickListener(v -> {
            isNeedPosition = -1;
            isManipulation = -1;
            idTa = -1;
            holder.txtTable.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.txtTable.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.iBtnCancelOperation.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return mTables.size();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Table table = mTables.get(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        int status = table.getStatus();

        if (isNeedPosition != -1){
            if (status != 2 && status != 3) {
                switch (isManipulation) {
                    case 0:
                        handleTransfer(holder, table, isLongClick);
                        break;
                    case 1:
                        handleMerge(holder, table, isLongClick);
                        break;
                }
            }else {
                showToast("Bàn này không được!", Toast.LENGTH_SHORT);
            }
        }else {
            if (!isLongClick) {
                Intent intent = new Intent(mContext, OrderActivity.class);
                intent.putExtra("status", table.getStatus())
                        .putExtra("table", table)
                        .putExtra("staff", staff);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                LinearLayout mTableFormView = mContext.findViewById(R.id.table_form);
                ProgressBar mProgressView = mContext.findViewById(R.id.table_progress);
                progress = new Progress(mContext, mTableFormView, mProgressView);
                progress.showProgress(true);
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    mContext.startActivity(intent);
                    progress.showProgress(false);
                }, 1500);
            }else if (staff.getKind_id() == 2 || staff.getKind_id() == 1){
                MenuBuilder menuBuilder = new MenuBuilder(mContext);
                MenuInflater inflater = new MenuInflater(mContext);
                inflater.inflate(R.menu.item_long_click, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(mContext, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);

                MenuItem item_booking = menuBuilder.findItem(R.id.item_booking);
                MenuItem item_contact = menuBuilder.findItem(R.id.item_contact);
                switch (status) {
                    case 1: {
                        item_booking.setVisible(false);
                        break;
                    }

                    default: {
                        if (status == 2) {
                            menuBuilder.findItem(R.id.item_time).setVisible(true);
                            item_contact.setVisible(true);
                            String title = item_contact.getTitle().toString();
                            item_contact.setTitle(title + table.getPhone_number());
                            item_booking.setTitle(string.action_cancel_booking);
                        }else if (status == 3){
                            item_booking.setVisible(false);
                        }
                        menuBuilder.findItem(R.id.item_transfer).setVisible(false);
                        menuBuilder.findItem(R.id.item_merge).setVisible(false);
//                        menuBuilder.findItem(R.id.item_finished_menu).setVisible(false);
                        break;
                    }
                }

                // Set Item Click Listener
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_transfer: {
                                if (status == 1) {
                                    isNeedPosition = position;
                                    showToast(mContext.getResources().getString(R.string.msg_select_table_to), Toast.LENGTH_SHORT);
                                    holder.txtTable.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                                    holder.txtTable.setTextColor(mContext.getResources().getColor(R.color.red));
                                    holder.iBtnCancelOperation.setVisibility(View.VISIBLE);
                                }
                                idTa = table.getId();
                                isManipulation = 0;
                                break;
                            }

                            case R.id.item_merge: {
                                if (status == 1) {
                                    isNeedPosition = position;
                                    showToast(mContext.getResources().getString(R.string.msg_select_table_to), Toast.LENGTH_SHORT);
                                    holder.txtTable.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                                    holder.txtTable.setTextColor(mContext.getResources().getColor(R.color.red));
                                    holder.iBtnCancelOperation.setVisibility(View.VISIBLE);
                                }
                                idTa = table.getId();
                                isManipulation = 1;
                                break;
                            }

                            case R.id.item_booking: {
                                if (!table.getTime_booking().isEmpty()) {
                                    table.setStatus(0);
                                    item_booking.setTitle(mContext.getResources().getString(string.action_booking));
                                    updateTable(table);
                                }else {
                                    chooseTime(table);
                                }
                                mIsBooking = !mIsBooking;
                                break;
                            }

//                            case R.id.item_finished_menu: {
//                                table.setStatus(3);
//                                updateTable(table);
//                                break;
//                            }

                            case R.id.item_time: {
                                chooseTime(table);
                                break;
                            }

                            case R.id.item_contact: {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + table.getPhone_number()));
                                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                                    mContext.startActivity(intent);
                                }
                                break;
                            }

                            default:
                                break;
                        }

                        return true;
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });
                // Display the menu
                optionsMenu.show();
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void chooseTime(Table table) {
        SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog pickerDialog = new TimePickerDialog(mContext, (view, hourOfDay, minute1) -> {
            if (hourOfDay > hour && hourOfDay < 21 && hourOfDay >= 7 || hourOfDay == hour && minute1 > minute) {
                calendar.set(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        hourOfDay, minute1, 0);
                String time = SIMPLE_DATE_FORMAT.format(calendar.getTime());

                inputPhoneNumberDialog(table, time);
            }else {
                showToast("Quá giờ đóng cửa hoặc bạn chọn sai!", Toast.LENGTH_SHORT);
            }
        }, hour, minute,true);

        pickerDialog.show();
    }

    private void inputPhoneNumberDialog(Table table, String time) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_custom_edittext);
        dialog.setCanceledOnTouchOutside(false);

        EditText edit_input = dialog.findViewById(R.id.edit_input);
        edit_input.setHint(mContext.getResources().getString(R.string.msg_customer_phone_number));

        edit_input.setText(!table.getPhone_number().isEmpty() ? table.getPhone_number() : "");

        dialog.findViewById(R.id.btn_ok).setOnClickListener(v -> {
            String phone = edit_input.getText().toString().trim();
            if (!phone.isEmpty()) {
                String[] phone_child = {phone.substring(0, 2), phone.substring(0, 3), phone.substring(0, 4)};
                boolean invalid = true;
                for (String string : STRINGS) {
                    if (phone.length() >= 10 && phone.length() < 12 && (phone_child[0].equals(string) || phone_child[1].equals(string) || phone_child[2].equals(string))) {
                        table.setStatus(2);
                        table.setPhone_number(phone);
                        table.setTime_booking(time);
                        updateTable(table);
                        invalid = false;
                        break;
                    }
                }
                if (invalid) {
                    edit_input.setError("Số bạn nhập không hợp lệ");
                }
            }else {
                edit_input.setError(mContext.getResources().getString(R.string.error_field_required));
            }
            dialog.dismiss();
        });

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void updateTable(Table table) {
        ApiTable apiTable = ApiClient.getClient().create(ApiTable.class);
        Call<Result> call = apiTable.updateStatusTable(table.getId(), table.getStatus(), table.getTime_booking(), table.getPhone_number());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                Result result = response.body();
                assert result != null;
                if (result.getSuccess()) {
                    mContext.getTableByAreaId(table.getArea());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                showToast(t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void handleMerge(ViewHolder holder, Table table2, boolean isLongClick) {
        if (!isLongClick) {
            if (table2.getStatus() != 0 && table2.getStatus() != 2) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mContext);
                }
                builder.setTitle(mContext.getResources().getString(R.string.action_confirm_merge));
                builder.setMessage(
                        mContext.getResources().getString(R.string.msg_are_you_sure) +" "+
                                mContext.getResources().getString(R.string.msg_mergeM)
                );
                builder.setPositiveButton("OK", (dialog, id) -> handleTable(holder, idTa, table2.getId(), "mergeTable.php"));
                builder.setNegativeButton("Cancel", (dialog, id) -> {

                });
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.show();
            }else {
                showToast(
                        mContext.getResources().getString(R.string.msg_please_choose_the_correct) +" "+
                                mContext.getResources().getString(R.string.msg_mergeM),
                        Toast.LENGTH_SHORT
                );
            }
        }
    }

    private void handleTransfer(ViewHolder holder, Table table2, boolean isLongClick) {
        if (!isLongClick) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(mContext);
            }
            builder.setTitle(mContext.getResources().getString(R.string.action_confirm_transfer));
            builder.setMessage(
                    mContext.getResources().getString(R.string.msg_are_you_sure) +" "+
                            mContext.getResources().getString(R.string.msg_transferM)
            );
            builder.setPositiveButton("OK", (dialog, id) -> handleTable(holder, idTa, table2.getId(), "switchTable.php"));
            builder.setNegativeButton("Cancel", (dialog, id) -> {

            });
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.show();
        }
    }

    private void handleTable(ViewHolder holder, int id1, int id2, String namePHP){
        ProgressDialog mProgressDialog = new ProgressDialog(mContext);
        ApiTable apiTable = ApiClient.getClient().create(ApiTable.class);
        Call<Result> call;
        if (!namePHP.equals("mergeTable.php")) {
            call = apiTable.handleTransfer(id1, id2);
            mProgressDialog.setTitle(mContext.getResources().getString(string.action_transfer));
        }else {
            call = apiTable.handleMerge(id1, id2);
            mProgressDialog.setTitle(mContext.getResources().getString(string.action_merge));
        }
        mProgressDialog.setMessage(mContext.getResources().getString(R.string.msg_loading));
        mProgressDialog.show();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                Result result = response.body();
                assert result != null;
                if (result.getSuccess()){
                    isNeedPosition = -1;
                    isManipulation = -1;
                    idTa = -1;
                    holder.txtTable.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    holder.txtTable.setTextColor(mContext.getResources().getColor(R.color.green));
                    mContext.getTableByAreaId(isArea);
                }else {
                    showToast(result.getMessage(), Toast.LENGTH_SHORT);
                }

                Handler handler = new Handler();
                handler.postDelayed(mProgressDialog::dismiss, 1500);
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                Log.e(SaleActivity.class.getSimpleName(), t.getMessage());
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private boolean setTime(TextView txtTime, String time_data, boolean isBooking) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        if (!time_data.equals("")){
            Date systemDate = Calendar.getInstance().getTime();
            String myDate = sdf.format(systemDate);

            Date dateNow = sdf.parse(myDate);
            Date dateData = sdf.parse(time_data);

            long mills = !isBooking ? Math.abs(dateNow.getTime() - dateData.getTime()) : dateData.getTime() - dateNow.getTime();

            if (mills > 0) {
                int Hours = (int) (mills/(1000 * 60 * 60));
                int Minutes = (int) (mills/(1000*60)) % 60;
                long Secs = (int) (mills / 1000) % 60;

                time = !isBooking ? Hours + ":" + Minutes + ":" + Secs + " " + mContext.getResources().getString(R.string.msg_ago) :
                        "Còn " + Hours + ":" + Minutes; // updated value every1 second
            }else { return false; }
        }
        txtTime.setText(time);
        return true;
    }

    private void showToast(String string, int length) {
        Toast.makeText(mContext, string, length).show();
    }

    private class CountDownRunner implements Runnable {
        private String time;
        private TextView txtTime;
        private Handler handler;
        private int sec;

        CountDownRunner(Handler handler, TextView txtTime, String time, int sec) {
            this.handler = handler;
            this.txtTime = txtTime;
            this.time = time;
            this.sec = sec;
        }

        // @Override
        public void run() {
            try {
                if (setTime(txtTime, time, sec != 1000))
                    handler.postDelayed(this, sec);
                else {
                    txtTime.setText("00:00");
                    Animation animRotate = AnimationUtils.loadAnimation(mContext, R.anim.flicker);
                    txtTime.startAnimation(animRotate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView txtTable, txtTime;
        private ImageView imgTable;
        private ImageButton iBtnCancelOperation;
        private FrameLayout frameGroup;

        private ItemClickListener itemClickListener;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            imgTable = itemView.findViewById(R.id.image_table);
            txtTable = itemView.findViewById(R.id.text_table);
            txtTime = itemView.findViewById(R.id.text_time);
            iBtnCancelOperation = itemView.findViewById(R.id.image_btn_cancel_operation);
            frameGroup = itemView.findViewById(R.id.frame_table);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
