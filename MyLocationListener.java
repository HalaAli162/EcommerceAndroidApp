package com.example.e4trely;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;

import androidx.annotation.NonNull;

public class MyLocationListener implements LocationListener {

    private Context activitycontext;
    public MyLocationListener(Context con){
        activitycontext=con;
    }
    @Override

    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
