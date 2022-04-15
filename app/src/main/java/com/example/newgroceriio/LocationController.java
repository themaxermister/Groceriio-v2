package com.example.newgroceriio;

import android.location.Location;
import android.util.Log;

public class LocationController {
    private static final String TAG = "LocationController";
    public static volatile LocationController mInstance = null;
    public double longitude = 0.0;
    public double latitude = 0.0;

    public static LocationController getInstance() {
        if (mInstance == null) {
            mInstance = new LocationController();
        }
        return mInstance;
    }

    public void setLongLat(double longitude, double latitude) {
        Log.e(TAG, "longitude: " + Double.toString(longitude) + ", latitude: " + Double.toString(latitude));
        longitude = longitude;
        latitude = latitude;
    }
}
