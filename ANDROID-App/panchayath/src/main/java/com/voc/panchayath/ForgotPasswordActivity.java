package com.voc.panchayath;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.voc.panchayath.api.ApiInteractor;
import com.voc.panchayath.api.ApiResponseListener;
import com.voc.panchayath.base.BaseActivity;
import com.voc.panchayath.model.ResponseWrapper;

public class ForgotPasswordActivity extends BaseActivity {

    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();

    private EditText etEmail;
    private Button btnResetPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Forgot Password");

        etEmail = findViewById(R.id.et_email);
        btnResetPassword = findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(view -> sendPassword());
    }

    private void sendPassword() {

        String email = etEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {
            shortToast("Please enter Email ID");
            return;
        }

        if (!isValidEmail(email)) {
            shortToast("Invalid email");
            return;
        }

        shortToast("Requesting for password recovery");

        ApiInteractor.resetPassword(
                email,
                new ApiResponseListener<ResponseWrapper<String>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<String> response) {
                        displayResponse(response);
                    }

                    @Override
                    public void onError(Throwable err) {
                        longToast(err.getMessage());
                        Log.e(TAG, "onError: ", err);
                    }
                }
        );

    }

    private void displayResponse(ResponseWrapper<String> response) {
        if (response != null) {
            if (response.isSuccess()) {
                shortToast(response.getResponse());
                finish();
            } else {
                longToast(response.getError());
            }
        }
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
