package com.example.locateme;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class myLocationListner implements LocationListener {
    Context context;

    public static Location location;

    public myLocationListner(Context context){
        this.context=context;

    }
    @Override
    public void onLocationChanged(Location location) {
        String Longitude_Value=String.valueOf(location.getLongitude());
        String Latitute_Value=String.valueOf(location.getLatitude());
//        Toast.makeText(context, Longitude_Value+" : "+Latitute_Value, Toast.LENGTH_SHORT).show();
    this.location=location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(context, "GPS status changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context, "GPS is enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context, "GPS is disabled", Toast.LENGTH_SHORT).show();
    }
}
