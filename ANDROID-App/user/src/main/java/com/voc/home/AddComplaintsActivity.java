package com.voc.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.voc.R;
import com.voc.api.ApiInteractor;
import com.voc.api.ApiResponseListener;
import com.voc.base.BaseActivity;
import com.voc.model.Citizen;
import com.voc.model.Complaint;
import com.voc.model.ResponseWrapper;
import com.voc.util.LocationTracker;
import com.voc.util.LocationUtil;
import com.voc.util.MySharedPreferences;
import com.voc.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class AddComplaintsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AddComplaintsActivity";
    private AddComplaintsActivity ctxt = AddComplaintsActivity.this;

    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final int PERMISSIONS_CODE = 101;

    private Button btnTakePicture, btnLocation, btnSubmit;
    private TextView tvCurrentLocation, tvComplaintAbout;
    private EditText etLandMark, etMobileNo, etDescription;
    private ProgressBar progressBar;

    private String currentLocation = null;
    private Complaint.Type complaintType;

    private LocationTracker tracker;

    private List<String> photoPathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaints);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Raise New Complaint");

        tracker = new LocationTracker(AddComplaintsActivity.this);

        progressBar = findViewById(R.id.progress_bar);
        btnTakePicture = findViewById(R.id.btn_take_picture);
        btnLocation = findViewById(R.id.btn_location);
        btnSubmit = findViewById(R.id.btn_submit);
        etLandMark = findViewById(R.id.et_land_mark);
        etMobileNo = findViewById(R.id.et_mobile);
        etDescription = findViewById(R.id.et_description);
        tvComplaintAbout = findViewById(R.id.tv_complaint_about);
        tvCurrentLocation = findViewById(R.id.tv_location);

        btnTakePicture.setOnClickListener(AddComplaintsActivity.this);
        btnLocation.setOnClickListener(AddComplaintsActivity.this);
        btnSubmit.setOnClickListener(AddComplaintsActivity.this);

        complaintType = (Complaint.Type) getIntent().getSerializableExtra("compType");

        tvComplaintAbout.setText(String.format("Write your complaint regarding %s issue", complaintType));
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tracker != null) {
            tracker.stopService();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_take_picture:
                startActivityForResult(new Intent(ctxt, AddImagesActivity.class), 110);
                break;
            case R.id.btn_location:
                trackLocation();
                break;
            case R.id.btn_submit:
                submitComplaint();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_CODE:
                if (grantResults.length > 0) {

                    StringBuilder permissionsDenied = new StringBuilder();

                    for (String per : permissions) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied.append("\n").append(per);
                        }
                    }

                    if (permissionsDenied.length() > 0) {
                        longToast("Please grant all the permissions required");
                    } else {
                        trackLocation();
                    }
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void trackLocation() {

        if (!PermissionUtil.hasPermissions(ctxt, PERMISSIONS, PERMISSIONS_CODE)) {
            return;
        }

        if (!isGpsEnabled()) {
            showConfirmation(
                    "Your GPS seems to be disabled, do you want to enable it?",
                    (dialog, which) -> {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 104);
                    });
            return;
        }

        if (tracker != null) {
            tracker.startService(
                    location -> {
                        shortToast("Found new location");
                        currentLocation = LocationUtil.getCompleteAddressString(
                                AddComplaintsActivity.this,
                                location.getLatitude(),
                                location.getLongitude()
                        );

                        tvCurrentLocation.setText(currentLocation);
                    }
            );
        }
    }


    private void submitComplaint() {

        if (!isValidInputs()) {
            return;
        }

        Citizen citizen = MySharedPreferences.getCitizenData(AddComplaintsActivity.this);

        if (citizen == null) {
            shortToast("Session error. Please re-login");
            return;
        }

        int citizenId = citizen.getRid();
        int panchayathId = citizen.getPanchayath();

        showProgress(true, btnSubmit, progressBar);

        ApiInteractor.submitComplaints(
                citizenId,
                panchayathId,
                complaintType,
                getMobile(),
                getPhotoPath(),
                getCurrentLocation(),
                getLandMark(),
                getDescription(),
                new ApiResponseListener<ResponseWrapper<String>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<String> response) {
                        showProgress(false, btnSubmit, progressBar);
                        displayResponse(response);
                    }

                    @Override
                    public void onError(Throwable err) {
                        showProgress(false, btnSubmit, progressBar);
                        Log.e(TAG, "onError: ", err);
                        longToast(err.getMessage());
                    }
                }
        );
    }

    private void displayResponse(ResponseWrapper<String> response) {
        if (response != null) {
            if (response.isSuccess()) {

                shortToast(response.getResponse());
                clearInputs();

            } else {
                shortToast(response.getError());
            }
        }
    }

    private void clearInputs() {
        tvCurrentLocation.setText("Current Location");
        etLandMark.setText("");
        etMobileNo.setText("");
        etDescription.setText("");
        photoPathList.clear();
        MySharedPreferences.saveImages(ctxt, photoPathList);
        currentLocation = null;
    }

    private boolean isValidInputs() {

        if (getPhotoPath().isEmpty()) {
            shortToast("Please take pictures");
            return false;
        }

        if (TextUtils.isEmpty(getCurrentLocation())) {
            shortToast("Please select current location");
            return false;
        }

        if (TextUtils.isEmpty(getLandMark())) {
            shortToast("Please enter land mark");
            return false;
        }

        if (TextUtils.isEmpty(getMobile()) || getMobile().length() != 10) {
            shortToast("Please enter valid mobile number");
            return false;
        }

        if (TextUtils.isEmpty(getDescription())) {
            shortToast("Please enter description");
            return false;
        }

        return true;
    }

    private String getLandMark() {
        return etLandMark.getText().toString();
    }

    private String getDescription() {
        return etDescription.getText().toString();
    }

    private List<String> getPhotoPath() {
        return photoPathList;
    }

    private String getMobile() {
        return etMobileNo.getText().toString();
    }

    private String getCurrentLocation() {
        // to do bypass GPS location
        return TextUtils.isEmpty(currentLocation) ? "Unknown Location" : currentLocation;
    }


    private boolean isGpsEnabled() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return (manager != null && manager.isProviderEnabled(LocationManager.GPS_PROVIDER));
    }
}
