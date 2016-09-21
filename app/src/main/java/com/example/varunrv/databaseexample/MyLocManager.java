package com.example.varunrv.databaseexample;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by vvadiraj on 9/21/2016.
 */
public class MyLocManager implements android.location.LocationListener {
    private Double lat;
    private Double lon;
    @Override
    public void onLocationChanged(Location location) {
        if(location !=null){
            Log.e("LAtitude:",""+location.getLatitude());
            lat=location.getLatitude();
            Log.e("Longitude:",""+location.getLongitude());
            lon=location.getLongitude();
        }
    }
    public String getLatitude(){
        return lat.toString();
    }
    public String getLongitude(){
        return lon.toString();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
