package com.voc.panchayath.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.voc.panchayath.LoginActivity;
import com.voc.panchayath.util.MySharedPreferences;
import com.voc.panchayath.util.UIHandlers;

public class BaseActivity extends AppCompatActivity implements DisplayCommons {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected AppCompatActivity context = BaseActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void shortToast(@NonNull String message) {
        UIHandlers.shortToast(BaseActivity.this, message);
    }

    @Override
    public void longToast(@NonNull String message) {
        UIHandlers.longToast(BaseActivity.this, message);
    }

    @Override
    public void shortSnack(@NonNull String message) {
        UIHandlers.shortSnackBar(getWindow().getDecorView(), message);
    }

    @Override
    public void longSnack(@NonNull String message) {
        UIHandlers.longSnackBar(getWindow().getDecorView(), message);
    }

    @Override
    public void longSnack(@NonNull String message, @NonNull View.OnClickListener listener) {
        UIHandlers.longSnackBar(getWindow().getDecorView(), message, listener);
    }

    @Override
    public void showConfirmation(@NonNull String title, @NonNull String body, @NonNull DialogInterface.OnClickListener listener) {
        UIHandlers.showConfirmation(context, title, body, listener);
    }

    @Override
    public void showConfirmation(@NonNull String body, @NonNull DialogInterface.OnClickListener listener) {
        UIHandlers.showConfirmation(context, null, body, listener);
    }

    protected void logout() {
        showConfirmation(
                "Are you sure want to logout?",
                (dialog, which) -> {
                    // clear login credentials from shared preferences and close the app
                    MySharedPreferences.logout(BaseActivity.this);
                    // redirect Login page
                    startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                    BaseActivity.this.finish();
                });
    }

    protected void showProgress(boolean show, View view, ProgressBar progressBar) {
        view.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
