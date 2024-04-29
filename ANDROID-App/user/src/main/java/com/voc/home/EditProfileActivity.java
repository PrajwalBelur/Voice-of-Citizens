package com.voc.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.voc.R;
import com.voc.api.ApiInteractor;
import com.voc.api.ApiResponseListener;
import com.voc.base.BaseActivity;
import com.voc.model.Citizen;
import com.voc.model.ResponseWrapper;
import com.voc.util.MySharedPreferences;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = EditProfileActivity.class.getSimpleName();

    private EditText etName, etPhone, etEmail, etPassword, etAddress;
    private TextView tvGender;
    private Button btnUpdate;
    private ProgressBar progressBar;

    private Citizen citizen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Edit Profile");

        progressBar = findViewById(R.id.progress_bar);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_mobile);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);
        etPassword = findViewById(R.id.et_password);
        tvGender = findViewById(R.id.tv_gender);
        btnUpdate = findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(EditProfileActivity.this);

        citizen = (Citizen) getIntent().getSerializableExtra("citizen");

        setData();
    }

    private void setData() {
        if (citizen != null) {
            etName.setText(citizen.getName());
            etPhone.setText(citizen.getContactWithoutCountryCode());
            etEmail.setText(citizen.getEmail());
            etAddress.setText(citizen.getAddress());
            tvGender.setText("Gender : " + citizen.getGender());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                update();
                break;
        }
    }

    private void update() {

        if (!isValidInputs()) {
            return;
        }

        int citizenRid = MySharedPreferences.getCitizenRid(EditProfileActivity.this);

        if (citizenRid == 0) {
            shortToast("Session error. Please re-login");
            return;
        }

        showProgress(true, btnUpdate, progressBar);

        ApiInteractor.updateProfile(
                citizenRid,
                getName(),
                getPassword(),
                getPhone(),
                getEmail(),
                getAddress(),
                new ApiResponseListener<ResponseWrapper<Citizen>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<Citizen> response) {
                        showProgress(false, btnUpdate, progressBar);
                        displayResponse(response);
                    }

                    @Override
                    public void onError(Throwable err) {
                        showProgress(false, btnUpdate, progressBar);
                        longToast(err.getMessage());
                        Log.e(TAG, "onError: ", err);
                    }
                }

        );
    }

    private void displayResponse(ResponseWrapper<Citizen> response) {
        if (response != null) {
            if (response.isSuccess()) {
                MySharedPreferences.saveCitizenData(EditProfileActivity.this, response.getResponse());
                finish();
            } else {
                longToast(response.getError());
            }
        }
    }

    private boolean isValidInputs() {

        if (TextUtils.isEmpty(getName())) {
            shortToast("Please enter name");
            return false;
        }

        if (TextUtils.isEmpty(getPhone()) || getPhone().length() != 10) {
            shortToast("Please enter valid 10 digits phone number");
            return false;
        }

        if (TextUtils.isEmpty(getEmail())) {
            shortToast("Please enter email");
            return false;
        }

        if (!isValidEmail(getEmail())) {
            shortToast("Invalid email");
            return false;
        }

        if (TextUtils.isEmpty(getPassword())) {
            shortToast("Please enter password");
            return false;
        }

        return true;
    }

    private String getName() {
        return etName.getText().toString();
    }

    private String getPhone() {
        return etPhone.getText().toString();
    }

    private String getEmail() {
        return etEmail.getText().toString();
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private String getPassword() {
        return etPassword.getText().toString();
    }

    private String getAddress() {
        return etAddress.getText().toString();
    }


}
