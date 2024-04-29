package com.voc.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class LocationUtil {

    private static final String TAG = LocationUtil.class.getSimpleName();

    public static String getCompleteAddressString(Context context, double latitude, double longitude) {

        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i));
                }

                strAdd = strReturnedAddress.toString();
                Log.i(TAG, strAdd);
            } else {
                Log.i(TAG, "No Address returned!");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Can'not get Address!", ex);
        }

        return strAdd;
    }

}
