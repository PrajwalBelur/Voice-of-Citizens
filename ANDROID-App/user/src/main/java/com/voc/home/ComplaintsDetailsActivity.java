package com.voc.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.voc.R;
import com.voc.api.ApiInteractor;
import com.voc.api.ApiResponseListener;
import com.voc.base.BaseActivity;
import com.voc.model.Complaint;
import com.voc.model.ResponseWrapper;

public class ComplaintsDetailsActivity extends BaseActivity {

    private static final String TAG = ComplaintsDetailsActivity.class.getSimpleName();
    private ComplaintsDetailsActivity ctxt = ComplaintsDetailsActivity.this;

    private TextView tvType, tvDesc, tvLocation, tvComplaintDateTime, tvComplaintStatus,
            tvIsSolved, tvSolvedDate, tvRejectionReason;
    private Button btnViewPictures, btnMarkAsCompleted;
    private ProgressBar progressBar;

    private Complaint complaint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_details);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Complaint Details");

        progressBar = findViewById(R.id.progress_bar);
        tvType = findViewById(R.id.tv_complaint_type);
        tvLocation = findViewById(R.id.tv_location);
        tvComplaintDateTime = findViewById(R.id.tv_complaint_date_time);
        tvComplaintStatus = findViewById(R.id.tv_complaint_status);
        tvIsSolved = findViewById(R.id.tv_is_solved);
        tvSolvedDate = findViewById(R.id.tv_solved_date);
        tvDesc = findViewById(R.id.tv_description);
        tvRejectionReason = findViewById(R.id.tv_reject_reason);
        btnViewPictures = findViewById(R.id.btn_view_pictures);
        btnMarkAsCompleted = findViewById(R.id.btn_mark_as_completed);

        complaint = (Complaint) getIntent().getSerializableExtra("complaint");
        if (complaint != null) {
            setData();
        } else {
            finish();
        }
    }

    private void setData() {
        tvType.setText(complaint.getType());
        tvLocation.setText(complaint.getLocation());
        tvComplaintDateTime.setText(complaint.getDateTime());
        tvComplaintStatus.setText(complaint.getComplaintStatus());
        tvSolvedDate.setText(complaint.getSolvedDate());
        tvDesc.setText(complaint.getDescription());
        tvRejectionReason.setText(complaint.getRejectionReason());

        btnMarkAsCompleted.setVisibility(complaint.isConfirmed() ? View.GONE : View.VISIBLE);

        boolean isComplete = complaint.isComplete();

        tvIsSolved.setText(isComplete ? "YES" : "NO");

        if (isComplete) {
            btnViewPictures.setVisibility(View.VISIBLE);
            btnViewPictures.setOnClickListener(view -> {
                Intent intent = new Intent(ctxt, CompletedImagesActivity.class);
                intent.putExtra("complaint", complaint);
                startActivityForResult(intent, 101);
            });

            btnMarkAsCompleted.setOnClickListener(view -> {

                showProgress(true, btnMarkAsCompleted, progressBar);

                ApiInteractor.markComplaintAsConfirmed(
                        complaint.getRid(),
                        new ApiResponseListener<ResponseWrapper<String>>() {
                            @Override
                            public void onSuccess(ResponseWrapper<String> response) {
                                showProgress(false, btnMarkAsCompleted, progressBar);
                                displayDeleteMessage(response);
                            }

                            @Override
                            public void onError(Throwable err) {
                                showProgress(false, btnMarkAsCompleted, progressBar);
                                longToast(err.getMessage());
                                Log.e(TAG, "onError: ", err);
                            }
                        }
                );
            });
        }
    }

    private void displayDeleteMessage(ResponseWrapper<String> response) {
        if (response != null) {
            if (response.isSuccess()) {

                shortToast(response.getResponse());

                // hide button
                btnMarkAsCompleted.setVisibility(View.GONE);

            } else {
                longToast(response.getError());
            }
        }
    }

    protected void showProgress(boolean show, View view, ProgressBar progressBar) {
        view.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
