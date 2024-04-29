package com.voc.base;

import android.content.DialogInterface;
import android.view.View;

import androidx.annotation.NonNull;

public interface DisplayCommons {

    void shortToast(@NonNull String message);

    void longToast(@NonNull String message);

    void shortSnack(@NonNull String message);

    void longSnack(@NonNull String message);

    void longSnack(@NonNull String message, @NonNull View.OnClickListener listener);

    void showConfirmation(@NonNull String title, @NonNull String body, @NonNull DialogInterface.OnClickListener listener);

    void showConfirmation(@NonNull String body, @NonNull DialogInterface.OnClickListener listener);
}
