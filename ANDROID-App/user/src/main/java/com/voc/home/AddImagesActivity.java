package com.voc.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.voc.R;
import com.voc.adapter.AddImagesAdapter;
import com.voc.base.BaseActivity;
import com.voc.util.FileUtil;
import com.voc.util.MySharedPreferences;
import com.voc.util.PermissionUtil;
import com.voc.util.RecyclerTouchListener;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AddImagesActivity extends BaseActivity
        implements RecyclerTouchListener.ClickListener, View.OnClickListener {

    private static final String TAG = "AddImagesActivity";
    private AddImagesActivity ctxt = AddImagesActivity.this;

    private static final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final int PERMISSIONS_CODE = 101;
    private static final int OPEN_CAMERA_CODE = 102;

    private Button btnAddPictures;
    private RecyclerView rvImages;
    private FloatingActionButton fabDone;

    private List<String> picturesPathList = new ArrayList<>();
    private AddImagesAdapter adapter = new AddImagesAdapter();
    private String photoPath = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images);

        fabDone = findViewById(R.id.fab_done);
        btnAddPictures = findViewById(R.id.btn_add_pictures);
        rvImages = findViewById(R.id.rv_images);
        rvImages.addOnItemTouchListener(new RecyclerTouchListener(
                ctxt, rvImages, ctxt
        ));
        rvImages.setAdapter(adapter);

        fabDone.setOnClickListener(ctxt);
        btnAddPictures.setOnClickListener(ctxt);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_pictures:
                openCamera();
                break;
            case R.id.fab_done:
                if (!picturesPathList.isEmpty()) {
                    MySharedPreferences.saveImages(ctxt, picturesPathList);
                }
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSelectedImages();
        Log.i(TAG, "onStart: ");
    }

    private void loadSelectedImages() {
        if (picturesPathList.isEmpty()) {
            picturesPathList.addAll(MySharedPreferences.getImagesList(ctxt));
        }

        if (!picturesPathList.isEmpty()) {
            adapter.addImages(picturesPathList);
            rvImages.setVisibility(View.VISIBLE);
        }
    }

    private void openCamera() {

        if (!PermissionUtil.hasPermissions(ctxt, PERMISSIONS, PERMISSIONS_CODE)) {
            return;
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = FileUtil.createImageFile(ctxt);
            } catch (IOException ex) {
                Log.e(TAG, "openCamera: ", ex);
                longToast(ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null && photoFile.exists()) {

                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                        Log.e(TAG, "openCamera: ", e);
                    }
                }

                photoPath = photoFile.getPath();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(cameraIntent, OPEN_CAMERA_CODE);
            }
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
                        openCamera();
                    }
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == OPEN_CAMERA_CODE && resultCode == RESULT_OK) {
            picturesPathList.add(photoPath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void onLongClick(int position) {
        picturesPathList.remove(position);
        adapter.removeImage(position);
        if (picturesPathList.isEmpty()) {
            rvImages.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!picturesPathList.isEmpty()) {
            MySharedPreferences.saveImages(ctxt, picturesPathList);
        }
        finish();
    }

}
