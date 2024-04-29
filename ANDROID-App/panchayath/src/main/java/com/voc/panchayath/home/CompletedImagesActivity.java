package com.voc.panchayath.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.voc.panchayath.R;
import com.voc.panchayath.adapter.CompletedImageAdapter;
import com.voc.panchayath.api.ApiInteractor;
import com.voc.panchayath.api.ApiResponseListener;
import com.voc.panchayath.base.BaseActivity;
import com.voc.panchayath.model.Complaint;
import com.voc.panchayath.model.ResponseWrapper;

import java.util.ArrayList;
import java.util.List;

public class CompletedImagesActivity extends BaseActivity {

    private static final String TAG = "CompletedImagesActivity";
    private CompletedImagesActivity ctxt = CompletedImagesActivity.this;

    private CompletedImageAdapter adapter;
    private RecyclerView rvImages;
    private ProgressBar progressBar;

    private List<String> urlList = new ArrayList<>();
    private Complaint complaint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_images);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Completed Photos");
        }

        progressBar = findViewById(R.id.progress_bar);
        rvImages = findViewById(R.id.rv_images);
        complaint = (Complaint) getIntent().getSerializableExtra("complaint");

        adapter = new CompletedImageAdapter();
        rvImages.setAdapter(adapter);

        if (complaint != null) {
            loadImages();
        } else {
            finish();
        }
    }

    private void loadImages() {

        int complaintRid = complaint.getRid();

        showProgress(true, rvImages, progressBar);

        ApiInteractor.loadCompletedImages(
                complaintRid, new ApiResponseListener<ResponseWrapper<List<String>>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<List<String>> response) {
                        displayResponse(response);
                    }

                    @Override
                    public void onError(Throwable err) {
                        showProgress(false, rvImages, progressBar);
                        Log.e(TAG, "onError: ", err);
                        longToast(err.getMessage());
                    }
                });
    }

    private void displayResponse(ResponseWrapper<List<String>> response) {
        if (response != null) {

            showProgress(false, rvImages, progressBar);

            if (response.isSuccess()) {

                if (!urlList.isEmpty()) {
                    urlList.clear();
                }

                urlList.addAll(response.getResponse());

                adapter.addImageList(urlList);

            } else {
                longToast(response.getError());
            }
        }
    }
}
