package com.voc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.voc.api.ApiInteractor;
import com.voc.api.ApiResponseListener;
import com.voc.base.BaseActivity;
import com.voc.home.HomeActivity;
import com.voc.model.Citizen;
import com.voc.model.ResponseWrapper;
import com.voc.util.MySharedPreferences;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText etLoginId, etPassword;
    private Button btnLogin, btnRegister, btnForgotPassword, btnPanchayathApp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Citizen citizen = MySharedPreferences.getCitizenData(LoginActivity.this);

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
        btnPanchayathApp = findViewById(R.id.btn_open_pan_app);
        btnRegister = findViewById(R.id.btn_register);
        btnForgotPassword = findViewById(R.id.btn_forgot_password);

        btnLogin.setOnClickListener(LoginActivity.this);
        btnPanchayathApp.setOnClickListener(LoginActivity.this);
        btnRegister.setOnClickListener(LoginActivity.this);
        btnForgotPassword.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_open_pan_app:
                openPanchayathApp();
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_forgot_password:
                forgotPassword();
                break;
        }
    }

    private void register() {
        startActivityForResult(new Intent(LoginActivity.this, RegistrationActivity.class), 1);
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
                new ApiResponseListener<ResponseWrapper<Citizen>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<Citizen> response) {
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

    private void displayResponse(ResponseWrapper<Citizen> response) {
        if (response != null) {
            if (response.isSuccess()) {

                Citizen citizen = response.getResponse();
                MySharedPreferences.saveCitizenData(LoginActivity.this, citizen);

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();

            } else {
                longToast(response.getError());
            }
        }
    }

    private boolean isValidInputs() {

        if (TextUtils.isEmpty(getLoginId())) {
            shortToast("Please enter email or contact number");
            return false;
        }

        String loginId = getLoginId();

        if (TextUtils.isDigitsOnly(loginId) && loginId.length() != 10) {
            shortToast("Invalid contact number");
            return false;
        }

        if (!TextUtils.isDigitsOnly(loginId) && !isValidEmail(loginId)) {
            shortToast("Invalid email id");
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

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void openPanchayathApp() {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.jd.panchayath");
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Panchayath app is not installed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error ", ex);
            Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
