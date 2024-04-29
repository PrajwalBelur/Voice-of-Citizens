package com.voc.panchayath;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.voc.panchayath.api.ApiInteractor;
import com.voc.panchayath.api.ApiResponseListener;
import com.voc.panchayath.base.BaseActivity;
import com.voc.panchayath.home.HomeActivity;
import com.voc.panchayath.model.Panchayath;
import com.voc.panchayath.model.ResponseWrapper;
import com.voc.panchayath.util.MySharedPreferences;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText etLoginId, etPassword;
    private Button btnLogin, btnForgotPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Panchayath citizen = MySharedPreferences.getPanchayathData(LoginActivity.this);

        // if already logged in redirect to Home
        if (citizen != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            return;
        }

        progressBar = findViewById(R.id.progress_bar);
        etLoginId = findViewById(R.id.et_login_id);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnForgotPassword = findViewById(R.id.btn_forgot_password);

        btnLogin.setOnClickListener(LoginActivity.this);
        btnForgotPassword.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_forgot_password:
                forgotPassword();
                break;
        }
    }

    private void forgotPassword() {
        startActivityForResult(new Intent(LoginActivity.this, ForgotPasswordActivity.class), 2);
    }

    private void login() {

        if (!isValidInputs()) {
            return;
        }

        showProgress(true, btnLogin, progressBar);

        ApiInteractor.login(
                getLoginId(),
                getPassword(),
                new ApiResponseListener<ResponseWrapper<Panchayath>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<Panchayath> response) {
                        showProgress(false, btnLogin, progressBar);
                        displayResponse(response);
                    }

                    @Override
                    public void onError(Throwable err) {
                        showProgress(false, btnLogin, progressBar);
                        longToast(err.getMessage());
                        Log.e(TAG, "onError: ", err);
                    }
                }
        );
    }

    private void displayResponse(ResponseWrapper<Panchayath> response) {
        if (response != null) {
            if (response.isSuccess()) {

                Panchayath panchayath = response.getResponse();
                MySharedPreferences.savePanchaythData(LoginActivity.this, panchayath);

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();

            } else {
                longToast(response.getError());
            }
        }
    }

    private boolean isValidInputs() {

        if (TextUtils.isEmpty(getLoginId())) {
            shortToast("Please enter Panchayath ID");
            return false;
        }

        if (TextUtils.isEmpty(getPassword())) {
            shortToast("Please enter password");
            return false;
        }

        return true;
    }

    private String getLoginId() {
        return etLoginId.getText().toString();
    }

    private String getPassword() {
        return etPassword.getText().toString();
    }

}
