package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.WorkingTime;
import com.example.tug_pc.restaurantmanagermini.model.response.Result;
import com.example.tug_pc.restaurantmanagermini.model.response.ResultWorking;
import com.example.tug_pc.restaurantmanagermini.model.response.WorkingTimeResponse;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiStaff;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiWorkingTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInformationStaffActivity extends AppCompatActivity {
    private static WorkingTime wkt = null;
    private static Staff sStaff = null;
    private ViewGroup linear_time_work;
    private TextView text_weekdays, text_time_work;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information_staff);

        setSupportActionBar(findViewById(R.id.tool_title_handling_staff));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        EditText edit_input_name_staff     =  findViewById(R.id.edit_input_name_staff);
        EditText edit_input_email_staff    =  findViewById(R.id.edit_input_email_staff);
        EditText edit_input_phone_staff    =  findViewById(R.id.edit_input_phone_staff);
        EditText edit_input_address_staff  =  findViewById(R.id.edit_input_address_staff);
        RadioGroup radioGroup              =  findViewById(R.id.radioGroup);
        TextView text_choose_working_hours =  findViewById(R.id.text_choose_working_hours);
        Button btn_change_password = findViewById(R.id.btn_change_password);
        linear_time_work = findViewById(R.id.linear_time_work);
        text_weekdays = findViewById(R.id.text_weekdays);
        text_time_work = findViewById(R.id.text_time_work);

        Bundle bundle = getIntent().getBundleExtra("bundle_staff");
        sStaff = (Staff) bundle.getSerializable("staff");
        boolean isEdit = bundle.getBoolean("isEdit");

        assert sStaff != null;
        switch (sStaff.getKind_id()) {
            case 1: {
                radioGroup.check(R.id.radio_manage);
                findViewById(R.id.radio_serve).setVisibility(View.GONE);
                findViewById(R.id.radio_cashier).setVisibility(View.GONE);
                break;
            }

            case 2: {
                radioGroup.check(R.id.radio_serve);
                findViewById(R.id.radio_manage).setVisibility(View.GONE);
                break;
            }

            case 3: {
                radioGroup.check(R.id.radio_cashier);
                findViewById(R.id.radio_manage).setVisibility(View.GONE);
                break;
            }

            default: findViewById(R.id.radio_manage).setVisibility(View.GONE); break;
        }

        if (isEdit) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.msg_edit_staff);
            edit_input_name_staff.setText(sStaff.getName());
            edit_input_email_staff.setText(sStaff.getEmail());
            edit_input_phone_staff.setText(sStaff.getPhone());
            edit_input_address_staff.setText(sStaff.getAddress());
            linear_time_work.setVisibility(View.VISIBLE);
            btn_change_password.setVisibility(View.VISIBLE);

            getWorkingTime(sStaff.get_id());
        }else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.msg_add_staff);
            linear_time_work.setVisibility(View.GONE);
            btn_change_password.setVisibility(View.GONE);
        }

        text_choose_working_hours.setOnClickListener(v -> {
            displayShowWorkingTimeDialog();
        });

        String[] strings = {
                "086", "096", "097", "098", "0162", "0163", "0164", "0165", "0166", "0167", "0168", "0169",
                "090", "093", "0120", "0121", "0122", "0126", "0128", "08966",
                "091", "094", "0123", "0124", "0125", "0127", "0129",
                "092", "0188", "0186",
                "099", "0199"};

        findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            String name = edit_input_name_staff.getText().toString().trim();
            String email = edit_input_email_staff.getText().toString().trim();
            String phone = edit_input_phone_staff.getText().toString().trim();

            if (name.isEmpty()) {
                edit_input_name_staff.setError(this.getResources().getString(R.string.error_field_required));
            }else if (email.isEmpty()) {
                edit_input_email_staff.setError(this.getResources().getString(R.string.error_field_required));
            }else if (phone.isEmpty()) {

                edit_input_phone_staff.setError(this.getResources().getString(R.string.error_field_required));
            }else if (phone.length() < 10) {
                edit_input_phone_staff.setError("Phone không hợp lệ");
            }else if (text_weekdays.getText().toString().trim().isEmpty()) {
                showToast("Bạn cần chọn giờ làm!");
            }else {
                boolean invalid = false;
                String[] phone_child = {phone.substring(0, 2), phone.substring(0, 3), phone.substring(0, 4)};
                for (String string : strings) {
                    if (!phone_child[0].equals(string) && !phone_child[1].equals(string) && !phone_child[2].equals(string)) {
                        invalid = true;
                    }else {
                        edit_input_name_staff.requestLayout();
                        edit_input_email_staff.requestLayout();
                        edit_input_phone_staff.requestLayout();
                        sStaff.setName(name);
                        sStaff.setEmail(email);
                        sStaff.setPhone(phone);
                        if (!isEdit) {
                            sStaff.setStaff_root(1);
                        }
                        sStaff.setAddress(edit_input_address_staff.getText().toString().trim());
                        int kind_id;
                        switch (radioGroup.getCheckedRadioButtonId()) {
                            case R.id.radio_manage: { kind_id = 1; break; }

                            case R.id.radio_serve: { kind_id = 2; break; }

                            case R.id.radio_cashier: { kind_id = 3; break; }

                            default: { kind_id = 4; break; }
                        }
                        sStaff.setKind_id(kind_id);

                        updateInformationStaff(null, sStaff, isEdit ? 2 : 1);
                        break;
                    }
                }
                if (invalid)
                    edit_input_phone_staff.setError("Số điện thoại không hợp lệ");

            }
        });

        btn_change_password.setOnClickListener(v -> {
            displayChangePasswordDialog();
        });
    }

    private void displayChangePasswordDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_change_password);

        EditText mEdit_input_pass_present = dialog.findViewById(R.id.edit_input_pass_present);
        EditText mEdit_input_pass_new = dialog.findViewById(R.id.edit_input_pass_new);
        EditText mEdit_retype_pass = dialog.findViewById(R.id.edit_retype_pass_new);

        Toolbar toolbar = dialog.findViewById(R.id.tool_title_change_password);
        toolbar.setTitle("Thay đổi mật khẩu");

        dialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            String mPresent = mEdit_input_pass_present.getText().toString().trim();
            String mInput_pass_new = mEdit_input_pass_new.getText().toString().trim();
            String mRetype_pass = mEdit_retype_pass.getText().toString().trim();

            if (mPresent.isEmpty()) mEdit_input_pass_present.setError(this.getResources().getString(R.string.error_empty));
            if (mInput_pass_new.isEmpty()) mEdit_input_pass_new.setError(this.getResources().getString(R.string.error_empty));
            if (mRetype_pass.isEmpty()) mEdit_retype_pass.setError(this.getResources().getString(R.string.error_empty));
            else {
                if (!mPresent.equals(sStaff.getPass())) {
                    mEdit_input_pass_present.setError("Mật khẩu không chính xác!");
                }else if (mInput_pass_new.equals(sStaff.getPass())) {
                    mEdit_input_pass_new.setError("Đây chính xác là mật khẩu củ!");
                }else if (!mRetype_pass.equals(mInput_pass_new)) {
                    mEdit_retype_pass.setError("Phần này không khớp với mật khẩu mới!");
                }else {
                    sStaff.setPass(mInput_pass_new);
                    updateInformationStaff(dialog, sStaff, 0);
                }
            }

        });

        dialog.findViewById(R.id.btn_clear).setOnClickListener(v -> {

            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateInformationStaff(Dialog dialog, Staff staff, int is) {
        ApiStaff apiStaff = ApiClient.getClient().create(ApiStaff.class);
        Call<Result> call =
                apiStaff.updateInformationStaff(is,
                        staff.get_id(), staff.getName(), staff.getEmail(),
                        is == 1 ? "123456" : staff.getPass(), staff.getPhone(), staff.getAddress(),
                        staff.getKind_id(), staff.getStaff_root());

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                Result result = response.body();
                assert result != null;
                if (result.getSuccess()) {
                    String text = "Thành công!";
                    if (is == 1) text += ", mật khẩu mặc định là: 123456";
                    Toast.makeText(UpdateInformationStaffActivity.this, text, Toast.LENGTH_LONG).show();
                    if (dialog == null) onBackPressed();
                    else dialog.dismiss();
                }
                else showToast(result.getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    public void getWorkingTime(int idStaff) {
        ApiWorkingTime apiWorkingTime = ApiClient.getClient().create(ApiWorkingTime.class);
        Call<WorkingTimeResponse> call = apiWorkingTime.getWorkingTime(idStaff);
        call.enqueue(new Callback<WorkingTimeResponse>() {
            @Override
            public void onResponse(@NonNull Call<WorkingTimeResponse> call, @NonNull Response<WorkingTimeResponse> response) {
                WorkingTimeResponse workingTimeResponse = response.body();
                assert workingTimeResponse != null;
                if (workingTimeResponse.getSuccess()) {
                    wkt = workingTimeResponse.getListWorkingTime().get(0);
                }else wkt = null;

                if (wkt != null) {
                    linear_time_work.setVisibility(View.VISIBLE);
                    String weekdays = convertWeekdaysToString(getApplicationContext(), wkt.getWeekdays());
                    String str_weekdays = wkt.getName()+"\n"+"("+weekdays+")";
                    text_weekdays.setText(str_weekdays);
                    String str_time_work = getString(R.string.msg_from2)+wkt.getFrom_hour()+"\n"+getString(R.string.msg_come)+wkt.getCome_hour();
                    text_time_work.setText(str_time_work);
                }else linear_time_work.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<WorkingTimeResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void displayShowWorkingTimeDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_update_working_time);

        Toolbar toolbar = dialog.findViewById(R.id.tool_title_working_time);
        toolbar.setTitle(this.getResources().getString(R.string.action_edit));

        EditText edit_input_name  = dialog.findViewById(R.id.edit_input_name);
        EditText edit_input_weekdays = dialog.findViewById(R.id.edit_input_weekdays);
        TextView text_from_time  = dialog.findViewById(R.id.text_from_time);
        TextView text_come_time = dialog.findViewById(R.id.text_come_time);

        if (wkt != null) {
            edit_input_name.setText(wkt.getName());

            String str = convertWeekdaysToString(this, wkt.getWeekdays());

            if (str.length() > 0) {
                edit_input_weekdays.setText(str);
            }

            text_from_time.setText(wkt.getFrom_hour());

            text_come_time.setText(wkt.getCome_hour());
        }

        text_from_time.setOnClickListener(v -> setTimePickerDialog(text_from_time, wkt != null ? wkt.getFrom_hour() : "07:00:00", true));
        text_come_time.setOnClickListener(v -> setTimePickerDialog(text_come_time, wkt != null ? wkt.getCome_hour() : "21:30:00", false));

        edit_input_weekdays.setOnClickListener(v -> {
            displayChooseWeekdaysDialog(edit_input_weekdays, wkt == null ? "" : wkt.getWeekdays());
        });

        dialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            String strWeekdays = edit_input_weekdays.getText().toString().trim();
            String[] split = strWeekdays.split(", ");
            StringBuilder stringBuilder = new StringBuilder();
            for (String aSplit : split) {
                if (aSplit.equals(this.getResources().getString(R.string.msg_monday))) {
                    stringBuilder.append("1,");
                }else if (aSplit.equals(this.getResources().getString(R.string.msg_tuesday))){
                    stringBuilder.append("2,");
                }else if (aSplit.equals(this.getResources().getString(R.string.msg_wednesday))){
                    stringBuilder.append("4,");
                }else if (aSplit.equals(this.getResources().getString(R.string.msg_thursday))){
                    stringBuilder.append("8,");
                }else if (aSplit.equals(this.getResources().getString(R.string.msg_friday))){
                    stringBuilder.append("16,");
                }else if (aSplit.equals(this.getResources().getString(R.string.msg_saturday))){
                    stringBuilder.append("32,");
                }else if (aSplit.equals(this.getResources().getString(R.string.msg_sunday))){
                    stringBuilder.append("64,");
                }
            }

            String intWeekdays = stringBuilder.toString();
            if (stringBuilder.length() > 0) {
                intWeekdays = stringBuilder.substring(0, stringBuilder.length() - 1);
            }

            if (wkt == null) {
                wkt = new WorkingTime();
            }

            wkt.setName(edit_input_name.getText().toString().trim());
            wkt.setWeekdays(intWeekdays);
            wkt.setFrom_hour(text_from_time.getText().toString().trim());
            wkt.setCome_hour(text_come_time.getText().toString().trim());

            updateWorkingTime(wkt.getId() == -1, dialog, wkt.getId(), wkt.getName(), wkt.getWeekdays(), wkt.getFrom_hour(), wkt.getCome_hour());
        });

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void displayChooseWeekdaysDialog(EditText edit_input_weekdays, String weekdays) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_choose_weekdays);
        dialog.setCanceledOnTouchOutside(false);

        CheckBox cbIsMonday = dialog.findViewById(R.id.checkbox_monday);
        CheckBox cbIsTuesday = dialog.findViewById(R.id.checkbox_tuesday);
        CheckBox cbIsWednesday = dialog.findViewById(R.id.checkbox_wednesday);
        CheckBox cbIsThursday = dialog.findViewById(R.id.checkbox_thursday);
        CheckBox cbIsFriday = dialog.findViewById(R.id.checkbox_friday);
        CheckBox cbIsSaturday = dialog.findViewById(R.id.checkbox_saturday);
        CheckBox cbIsSunday = dialog.findViewById(R.id.checkbox_sunday);

        String[] split = edit_input_weekdays.getText().toString().trim().split(",");

        for (String aSplit : split) {
            if (aSplit.trim().equals(this.getResources().getString(R.string.msg_monday))) {
                cbIsMonday.setChecked(true);
            }else if (aSplit.trim().equals(this.getResources().getString(R.string.msg_tuesday))){
                cbIsTuesday.setChecked(true);
            }else if (aSplit.trim().equals(this.getResources().getString(R.string.msg_wednesday))){
                cbIsWednesday.setChecked(true);
            }else if (aSplit.trim().equals(this.getResources().getString(R.string.msg_thursday))){
                cbIsThursday.setChecked(true);
            }else if (aSplit.trim().equals(this.getResources().getString(R.string.msg_friday))){
                cbIsFriday.setChecked(true);
            }else if (aSplit.trim().equals(this.getResources().getString(R.string.msg_saturday))){
                cbIsSaturday.setChecked(true);
            }else if (aSplit.trim().equals(this.getResources().getString(R.string.msg_sunday))){
                cbIsSunday.setChecked(true);
            }
        }

        dialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            String strResult = (cbIsMonday.isChecked() ? this.getResources().getString(R.string.msg_monday)+", " : "") +
                    (cbIsTuesday.isChecked() ? this.getResources().getString(R.string.msg_tuesday)+", " : "") +
                    (cbIsWednesday.isChecked() ? this.getResources().getString(R.string.msg_wednesday)+", " : "") +
                    (cbIsThursday.isChecked() ? this.getResources().getString(R.string.msg_thursday)+", " : "") +
                    (cbIsFriday.isChecked() ? this.getResources().getString(R.string.msg_friday)+", " : "") +
                    (cbIsSaturday.isChecked() ? this.getResources().getString(R.string.msg_saturday)+", " : "") +
                    (cbIsSunday.isChecked() ? this.getResources().getString(R.string.msg_sunday)+", " : "");
            if (!strResult.isEmpty()) {
                strResult = strResult.trim().substring(0, strResult.length()-2);
            }else strResult = "";
            if (!strResult.equals(weekdays)) {
                edit_input_weekdays.setText(strResult);
            }
            dialog.dismiss();
        });

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void updateWorkingTime(boolean isNull, Dialog dialog, int id, String name, String weekdays, String from_hour, String come_hour) {
        ApiWorkingTime apiWorkingTime = ApiClient.getClient().create(ApiWorkingTime.class);
        Call<ResultWorking> call = apiWorkingTime.updateWorkingTime(isNull ? 1 : 2, id, sStaff.get_id(), name, weekdays, from_hour, come_hour);
        call.enqueue(new Callback<ResultWorking>() {
            @Override
            public void onResponse(@NonNull Call<ResultWorking> call, @NonNull Response<ResultWorking> response) {
                ResultWorking result = response.body();
                assert result != null;
                if (result.getSuccess()) {
                    getWorkingTime(sStaff.get_id());
                    dialog.dismiss();
                }
                else showToast(result.getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<ResultWorking> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void setTimePickerDialog(TextView text_time, String workingTime, boolean begins) {
        SimpleDateFormat format =  new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(workingTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog =
                new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                    calendar.set(0, 0, 0, hourOfDay, minute);
                    text_time.setText(format.format(calendar.getTime()));

                }, hour, min, true);
        timePickerDialog.setTitle(begins ? "Time begins..." : "Time end...");
        timePickerDialog.show();
    }

    public static String convertWeekdaysToString(Context context, String weekdays) {
        String[] split = weekdays.split(",");
        StringBuilder stringBuilder = new StringBuilder();

        for (String aSplit : split) {
            switch (aSplit) {
                case "1": stringBuilder.append(context.getResources().getString(R.string.msg_monday)); break;

                case "2": stringBuilder.append(context.getResources().getString(R.string.msg_tuesday)); break;

                case "4": stringBuilder.append(context.getResources().getString(R.string.msg_wednesday)); break;

                case "8": stringBuilder.append(context.getResources().getString(R.string.msg_thursday)); break;

                case "16": stringBuilder.append(context.getResources().getString(R.string.msg_friday)); break;

                case "32": stringBuilder.append(context.getResources().getString(R.string.msg_saturday)); break;

                case "64": stringBuilder.append(context.getResources().getString(R.string.msg_sunday)); break;
            }
            if (!aSplit.equals(split[split.length - 1])) {
                stringBuilder.append(", ");
            }
        }

        return String.valueOf(stringBuilder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sStaff = null;
        wkt = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: onBackPressed(); return true;
        }
        return false;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
