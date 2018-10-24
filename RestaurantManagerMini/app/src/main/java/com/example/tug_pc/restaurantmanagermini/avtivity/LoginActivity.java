package com.example.tug_pc.restaurantmanagermini.avtivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.data.StaffRepository;
import com.example.tug_pc.restaurantmanagermini.data.clients.ApiClient;
import com.example.tug_pc.restaurantmanagermini.data.local.StaffDatabase;
import com.example.tug_pc.restaurantmanagermini.data.local.StaffLocalDataSource;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiConnect;
import com.example.tug_pc.restaurantmanagermini.data.services.ApiStaff;
import com.example.tug_pc.restaurantmanagermini.model.Staff;
import com.example.tug_pc.restaurantmanagermini.model.response.Connect;
import com.example.tug_pc.restaurantmanagermini.model.response.StaffResponse;
import com.example.tug_pc.restaurantmanagermini.ultil.Progress;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CommitPrefEdits")
public class LoginActivity extends AppCompatActivity
        implements View.OnFocusChangeListener, View.OnClickListener{
    private StaffRepository mStaffRepository;
    private CompositeDisposable mCompositeDisposable;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private Progress mProgress;
    private Intent mIntent;

    private Staff mStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = findViewById(R.id.edit_email);
        mPasswordView = findViewById(R.id.edit_password);
        Button mEmailSignInButton = findViewById(R.id.button_sign_in);

        ViewGroup mLoginFormView = findViewById(R.id.login_form);
        View mProgressView = findViewById(R.id.login_progress);
        mProgress = new Progress(this, mLoginFormView, mProgressView);

        StaffDatabase staffDatabase = StaffDatabase.getInstance(this);
        mStaffRepository =
                StaffRepository.getInstance(StaffLocalDataSource.getInstance(staffDatabase.staffDAO()));
        mCompositeDisposable = new CompositeDisposable();

        mEmailView.setOnFocusChangeListener(this);
        mPasswordView.setOnFocusChangeListener(this);
        mEmailSignInButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Boolean isRestart = getIntent().getBooleanExtra("logout", false);
        if (isRestart) deleteAll();
    }

    private void checkAccount(String email, String password) {
        ApiStaff apiStaff = ApiClient.getClient().create(ApiStaff.class);
        Call<StaffResponse> call = apiStaff.login(email, password);
        call.enqueue(new Callback<StaffResponse>() {
            @Override
            public void onResponse(@NonNull Call<StaffResponse> call, @NonNull retrofit2.Response<StaffResponse> response) {
                StaffResponse staffResponse = response.body();
                assert staffResponse != null;
                if (staffResponse.getSuccess()) {
                    mStaff = staffResponse.getListStaff().get(0);
                    insert(mStaff);
                }else {
                    showToast("Sai tên tài khoản hoặc mật khẩu!");
                    mProgress.showProgress(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StaffResponse> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    public void insert(final Staff staff) {
        Disposable disposable = Observable.create(e -> {
            mStaffRepository.insertStaff(staff);
            e.onComplete();
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Consumer<? super Object>) o -> {
                }, throwable -> showToast(throwable.getMessage()), this::getData);

        mCompositeDisposable.add(disposable);
    }

    private void getData() {
        mIntent = new Intent(LoginActivity.this, MainActivity.class);
        Disposable disposable = mStaffRepository.loadingStaff()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(staffs -> {
                    if (staffs.size() > 0) {
                        SharedPreferences pre = getSharedPreferences("my_user", MODE_PRIVATE);
                        SharedPreferences.Editor edit = pre.edit();
                        edit.putBoolean("logged_on", true);
                        edit.apply();
                        mIntent.putExtra("staff", staffs.get(0));
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            mProgress.showProgress(false);
                            startActivity(mIntent);
                        }, 1500);
                    }
//                        onGetAllUserSuccess(staffs);
                }, throwable -> {});

        mCompositeDisposable.add(disposable);
    }

    public void deleteAll() {
        SharedPreferences pre = getSharedPreferences("my_user", MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        edit.clear();
        edit.apply();
        Disposable disposable = Observable.create(e -> {
            mStaffRepository.deleteAllStaff();
            e.onComplete();
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Consumer<? super Object>) o -> {
                    //no ops
                }, throwable -> {});

        mCompositeDisposable.add(disposable);
    }

    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mProgress.showProgress(true);
            checkAccount(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onClick(View v) {
        ApiConnect apiConnect = ApiClient.getClient().create(ApiConnect.class);
        Call<Connect> call = apiConnect.haveConnection();
        call.enqueue(new Callback<Connect>() {
            @Override
            public void onResponse(@NonNull Call<Connect> call, @NonNull Response<Connect> response) {
                Connect connect = response.body();
                assert connect != null;
                if (connect.getSuccess()) {
                    attemptLogin();
                }else {
                    showToast("Không có kết nối!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Connect> call, @NonNull Throwable t) {
                showToast("Không có kết nối!");
                Log.e("TAG - res", t.getMessage()) ;
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edit_email:
                mEmailView.setCursorVisible(true);
                mPasswordView.setCursorVisible(false);
                break;

            case R.id.edit_password:
                mEmailView.setCursorVisible(false);
                mPasswordView.setCursorVisible(true);
                break;
        }
    }

    private void showToast(String toast){
        if (!toast.isEmpty())
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}