package com.voc.panchayath.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.voc.panchayath.R;
import com.voc.panchayath.api.ApiInteractor;
import com.voc.panchayath.api.ApiResponseListener;
import com.voc.panchayath.base.BaseActivity;
import com.voc.panchayath.model.Complaint;
import com.voc.panchayath.model.ResponseWrapper;
import com.voc.panchayath.util.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ComplaintsDetailsActivi";
    private ComplaintsDetailsActivity ctxt = ComplaintsDetailsActivity.this;

    private TextView tvType, tvDesc, tvLocation, tvComplaintDateTime, tvComplaintStatus,
            tvIsSolved, tvSolvedDate, tvRejectionReason;
    private Button btnAddPictures, btnMarkAsComplete, btnViewPictures;
    private ProgressBar progressBar;

    private Complaint complaint;

    private List<String> photoPathList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_details);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Complaint Details");

        progressBar = findViewById(R.id.progress_bar);
        btnAddPictures = findViewById(R.id.btn_add_pictures);
        btnMarkAsComplete = findViewById(R.id.btn_mark_as_completed);
        tvType = findViewById(R.id.tv_complaint_type);
        tvType = findViewById(R.id.tv_complaint_type);
        tvLocation = findViewById(R.id.tv_location);
        tvComplaintDateTime = findViewById(R.id.tv_complaint_date_time);
        tvComplaintStatus = findViewById(R.id.tv_complaint_status);
        tvIsSolved = findViewById(R.id.tv_is_solved);
        tvSolvedDate = findViewById(R.id.tv_solved_date);
        tvDesc = findViewById(R.id.tv_description);
        tvRejectionReason = findViewById(R.id.tv_reject_reason);
        btnViewPictures = findViewById(R.id.btn_view_pictures);

        btnAddPictures.setOnClickListener(ctxt);
        btnMarkAsComplete.setOnClickListener(ctxt);

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
        tvIsSolved.setText(complaint.isComplete() ? "YES" : "NO");
        btnAddPictures.setVisibility(complaint.isAccepted() ? View.VISIBLE : View.GONE);

        if (complaint.isComplete()) {
            btnViewPictures.setVisibility(View.VISIBLE);
            btnViewPictures.setOnClickListener(view -> {
                Intent intent = new Intent(ctxt, CompletedImagesActivity.class);
                intent.putExtra("complaint", complaint);
                startActivityForResult(intent, 101);
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSelectedImages();
        Log.i(TAG, "onStart: ");
    }

    private void loadSelectedImages() {
        if (photoPathList.isEmpty()) {
            photoPathList.addAll(MySharedPreferences.getImagesList(ctxt));
        }

        if (!photoPathList.isEmpty()) {
            btnMarkAsComplete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_pictures:
                startActivityForResult(new Intent(ctxt, AddImagesActivity.class), 101);
                break;
            case R.id.btn_mark_as_completed:
                markAsComplete();
                break;
        }
    }

    private void markAsComplete() {

        if (getPhotoPathList().isEmpty()) {
            shortToast("Please attach images");
            return;
        }

        showProgress(true, btnMarkAsComplete, progressBar);

        ApiInteractor.markComplaintAsConfirmed(
                complaint.getRid(),
                getPhotoPathList(),
                new ApiResponseListener<ResponseWrapper<String>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<String> response) {
                        showProgress(false, btnMarkAsComplete, progressBar);
                        parseResponse(response);

                    }

                    @Override
                    public void onError(Throwable err) {
                        showProgress(false, btnMarkAsComplete, progressBar);
                        longToast(err.getMessage());
                        Log.e(TAG, "onError: ", err);
                    }
                }
        );
    }

    private List<String> getPhotoPathList() {
        return photoPathList;
    }

    private void parseResponse(ResponseWrapper<String> response) {
        if (response != null) {
            if (response.isSuccess()) {
                photoPathList.clear();
                MySharedPreferences.saveImages(ctxt, photoPathList);
                shortToast(response.getResponse());
                finish();
            } else {
                longToast(response.getError());
            }
        }
    }
}
