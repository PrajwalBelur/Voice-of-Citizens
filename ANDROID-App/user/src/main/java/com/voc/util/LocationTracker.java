package com.voc.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class LocationTracker implements LocationListener {

    private static final String TAG = LocationTracker.class.getSimpleName();

    private static final int LOCATION_INTERVAL = 1000;  // in milliseconds
    private static final float LOCATION_DISTANCE = 0f;  // in meters

    private Context context;
    private Location lastLocation;
    private LocationManager locationManager;
    private LocationUpdatesListener listener;

    private boolean isServiceAlreadyStarted = false;

    public LocationTracker(Context context) {
        this.context = context;
        lastLocation = new Location(LocationManager.NETWORK_PROVIDER);
    }

    public void startService() throws SecurityException {
        try {

            if (locationManager == null) {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            }

            if (isServiceAlreadyStarted) {  // service is already active
                return;
            }

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    LocationTracker.this
            );

            isServiceAlreadyStarted = true;

        } catch (Exception ex) {
            Log.i(TAG, "Failed to request location update, ", ex);
        }
    }

    public void startService(LocationUpdatesListener listener) throws SecurityException {
        this.listener = listener;
        startService();
    }

    public Location getLocation() {
        return lastLocation;
    }

    public void stopService() {
        if (locationManager != null) {
            try {

                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                locationManager.removeUpdates(LocationTracker.this);

            } catch (Exception ex) {
                Log.e(TAG, "fail to remove location listener, ignore", ex);
            }

            if (listener != null) {
                listener = null;
            }

            isServiceAlreadyStarted = false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged: " + location);
        lastLocation.set(location);
        if (listener != null) {
            listener.onNewLocation(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "onStatusChanged: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "onProviderDisabled: " + provider);
    }

    public interface LocationUpdatesListener {
        void onNewLocation(Location location);
    }
}
