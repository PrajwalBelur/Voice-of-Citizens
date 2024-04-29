package com.voc.panchayath.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.voc.panchayath.util.UIHandlers;

public class BaseFragment extends Fragment implements DisplayCommons {

    private static final String TAG = BaseFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected final void setTitle(String title) {
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void shortToast(@NonNull String message) {
        UIHandlers.shortToast(getActivity(), message);
    }

    @Override
    public void longToast(@NonNull String message) {
        UIHandlers.longToast(getActivity(), message);
    }

    @Override
    public void shortSnack(@NonNull String message) {
        if (getActivity() != null)
            UIHandlers.shortSnackBar(getActivity().findViewById(android.R.id.content), message);
    }

    @Override
    public void longSnack(@NonNull String message) {
        if (getActivity() != null)
            UIHandlers.longSnackBar(getActivity().findViewById(android.R.id.content), message);
    }

    @Override
    public void longSnack(@NonNull String message, @NonNull View.OnClickListener listener) {
        if (getActivity() != null)
            UIHandlers.longSnackBar(getActivity().findViewById(android.R.id.content), message, listener);
    }

    @Override
    public void showConfirmation(@NonNull String title, @NonNull String body, @NonNull DialogInterface.OnClickListener listener) {
        UIHandlers.showConfirmation(getActivity(), title, body, listener);
    }

    @Override
    public void showConfirmation(@NonNull String body, @NonNull DialogInterface.OnClickListener listener) {
        UIHandlers.showConfirmation(getActivity(), null, body, listener);
    }

    protected void showProgress(boolean show, View view, ProgressBar progressBar) {
        view.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
