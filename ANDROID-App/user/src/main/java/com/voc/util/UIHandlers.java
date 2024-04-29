package com.voc.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

public final class UIHandlers {

    public static void shortToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    private static void showSnackBar(View view, String message, View.OnClickListener listener, int length) {
        Snackbar snackbar = Snackbar.make(view, message, length);
        if (listener != null) {
            snackbar.setAction("OK", listener);
        }
        snackbar.show();
    }

    public static void shortSnackBar(View view, String message) {
        showSnackBar(view, message, null, Snackbar.LENGTH_SHORT);
    }

    public static void longSnackBar(View view, String message) {
        showSnackBar(view, message, null, Snackbar.LENGTH_LONG);
    }

    public static void longSnackBar(View view, String message, View.OnClickListener listener) {
        showSnackBar(view, message, listener, Snackbar.LENGTH_LONG);
    }

    public static void showLoading(ProgressBar bar) {

    }

    public static void showConfirmation(Activity activity, String title,
                                        String body, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(body);
        builder.setPositiveButton("YES", listener);
        builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
