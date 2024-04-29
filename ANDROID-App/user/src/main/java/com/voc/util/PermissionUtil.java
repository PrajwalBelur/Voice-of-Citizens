package com.voc.util;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

    public static boolean hasPermissions(AppCompatActivity ctxt, String[] permissions, int permissionCode) {

        if (!marshMallowOrAbove()) return true;

        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(ctxt, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(ctxt, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), permissionCode);
            return false;
        }

        return true;
    }

    public static boolean marshMallowOrAbove() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

}
