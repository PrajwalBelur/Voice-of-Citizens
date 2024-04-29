package com.voc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.voc.api.ApiInteractor;
import com.voc.api.ApiResponseListener;
import com.voc.base.BaseActivity;
import com.voc.model.District;
import com.voc.model.Panchayath;
import com.voc.model.RegPageDetails;
import com.voc.model.ResponseWrapper;
import com.voc.model.Taluk;
import com.voc.sqlite.SQLiteUtil;
import com.voc.util.FileUtil;
import com.voc.util.SpinnerHelper;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = RegistrationActivity.class.getSimpleName();

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSIONS_CODE = 101;
    private static final int PHOTO_PICK_CODE = 102;

    private int currentStoragePickerCode = 0;

    private Spinner spinPanchayath, spinTaluk, spinDistrict;
    private EditText etName, etPhone, etEmail, etPassword, etAddress;
    private RadioGroup rgGender;
    private Button btnRegister, btnPhoto;

    private String photoPath = null;

    private List<District> districtList = new ArrayList<>();
    private List<Taluk> talukList = new ArrayList<>();
    private List<Panchayath> panchayathList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBar = findViewById(R.id.progress_bar);
        spinDistrict = findViewById(R.id.spin_district);
        spinTaluk = findViewById(R.id.spin_taluk);
        spinPanchayath = findViewById(R.id.spin_gram_panchayath);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_mobile);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);
        etPassword = findViewById(R.id.et_password);
        rgGender = findViewById(R.id.rg_gender);
        btnRegister = findViewById(R.id.btn_register);
        btnPhoto = findViewById(R.id.btn_photo);

        btnRegister.setOnClickListener(RegistrationActivity.this);
        btnPhoto.setOnClickListener(RegistrationActivity.this);

        setSpinnerListeners();

        loadRegPageDetails();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                doRegister();
                break;
            case R.id.btn_photo:
                pickProfile();
                break;
        }
    }

    private void doRegister() {

        if (!isValidInputs()) {
            return;
        }

        showProgress(true, btnRegister, progressBar);

        ApiInteractor.register(
                getPanchayath(),
                getName(),
                getPassword(),
                getPhone(),
                getGender(),
                getEmail(),
                getAddress(),
                getPhotoPath(),
                new ApiResponseListener<ResponseWrapper<String>>() {
                    @Override
                    public void onSuccess(ResponseWrapper<String> response) {
                        showProgress(false, btnRegister, progressBar);
                        displayResponse(response);
                    }

                    @Override
                    public void onError(Throwable err) {
                        showProgress(false, btnRegister, progressBar);
                        longToast(err.getMessage());
                    }
                }
        );
    }

    private void displayResponse(ResponseWrapper<String> response) {
        if (response != null) {
            if (response.isSuccess()) {
                longToast(response.getResponse());
                finish();   // navigate to LoginActivity
            } else {
                longToast(response.getError());
            }
        }
    }

    private boolean isValidInputs() {

        if (TextUtils.isEmpty(getName())) {
            shortToast("Please enter name");
            return false;
        }

        if (TextUtils.isEmpty(getPhone()) || getPhone().length() != 10) {
            shortToast("Please enter valid 10 digits phone number");
            return false;
        }

        if (!TextUtils.isEmpty(getEmail()) && !isValidEmail(getEmail())) {
            shortToast("Invalid email");
            return false;
        }

        if (TextUtils.isEmpty(getPassword())) {
            shortToast("Please enter password");
            return false;
        }

        if (TextUtils.isEmpty(getPhotoPath())) {
            shortToast("Please select profile picture");
            return false;
        }

        return true;
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
                        if (currentStoragePickerCode == PHOTO_PICK_CODE) pickProfile();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case PHOTO_PICK_CODE:
                if (RESULT_OK == resultCode) {
                    shortToast("Photo picked");
                    photoPath = FileUtil.getPath(RegistrationActivity.this, data.getData());
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }

    private void pickProfile() {

        currentStoragePickerCode = PHOTO_PICK_CODE;

        boolean hasPermissions = checkPermissions();

        if (!hasPermissions) {
            return;
        }

        openGallery(PHOTO_PICK_CODE);
    }

    private void loadRegPageDetails() {
        shortToast("Please wait...");

        ApiInteractor.loadRegPageDetails(new ApiResponseListener<ResponseWrapper<RegPageDetails>>() {
            @Override
            public void onSuccess(ResponseWrapper<RegPageDetails> response) {
                if (response != null) {
                    if (response.isSuccess()) {
                        SQLiteUtil.saveRegPageDetails(RegistrationActivity.this, response.getResponse());
                        displayDistrict();
                    } else {
                        longToast(response.getError());
                    }
                }
            }

            @Override
            public void onError(Throwable err) {
                Log.e(TAG, "onError: ", err);
                longToast(err.getMessage());
            }
        });
    }

    private void loadTaluk(int districtId) {

        shortToast("Getting Taluk list please wait...");

        talukList = SQLiteUtil.getTalukList(districtId);
        SpinnerHelper.setTaluk(spinTaluk, talukList);
    }

    private void loadGramaList(int talukId) {

        shortToast("Getting Panchayath list please wait...");

        panchayathList = SQLiteUtil.getPanchayathList(talukId);
        SpinnerHelper.setPanchayaths(spinPanchayath, panchayathList);
    }

    private void displayDistrict() {
        districtList = SQLiteUtil.getDistrictList();
        SpinnerHelper.setDistricts(spinDistrict, districtList);
    }

    private int getPanchayath() {
        int pos = spinPanchayath.getSelectedItemPosition();
        return panchayathList.get(pos).getRid();
    }

    private String getName() {
        return etName.getText().toString();
    }

    private String getPhone() {
        return etPhone.getText().toString();
    }

    private String getEmail() {
        return etEmail.getText().toString();
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private String getPassword() {
        return etPassword.getText().toString();
    }

    private char getGender() {
        int id = rgGender.getCheckedRadioButtonId();
        return (id == R.id.rb_male) ? 'M' : 'F';
    }

    private String getAddress() {
        return etAddress.getText().toString();
    }

    private String getPhotoPath() {
        return photoPath;
    }

    private boolean checkPermissions() {

        if (!marshMallowOrAbove()) return true;

        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String permission : PERMISSIONS) {
            result = ContextCompat.checkSelfPermission(RegistrationActivity.this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSIONS_CODE);
            return false;
        }

        return true;
    }

    private boolean marshMallowOrAbove() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    private void setSpinnerListeners() {
        spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadTaluk(districtList.get(position).getRid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinTaluk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadGramaList(talukList.get(position).getRid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
