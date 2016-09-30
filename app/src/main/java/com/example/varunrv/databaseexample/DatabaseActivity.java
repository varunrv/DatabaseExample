package com.example.varunrv.databaseexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


public class DatabaseActivity extends AppCompatActivity {
    DBHelp mydb;
    String address;
    double latitude, longitude;
    LocationManager locationManager;
    MyLocManager loc = new MyLocManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        mydb = new DBHelp(DatabaseActivity.this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocManager loc;
        loc = new MyLocManager();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, loc);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
            OnTasks();
        } else {
            Toast.makeText(this, "GPS is not Enabled in your devide", Toast.LENGTH_SHORT).show();
            OffTasks();
        }
    }

    public void OnTasks() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, loc);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        latitude = 28.73234435;
        longitude = 70.24413438;
        address = "kochi";
        Log.d("val", address);
        long i = mydb.insert(address, String.valueOf(latitude), String.valueOf(longitude));
        String s = String.valueOf(i);
        Log.d("values", s);
        StringBuffer buffer1 = new StringBuffer();
        buffer1.append("Address :" + address + "\n");
        buffer1.append("Latitude :" + latitude + "\n");
        buffer1.append("Longitude :" + longitude + "\n\n");
        displayLocation("Current Location", buffer1.toString());
    }

    public void OffTasks() {
        Cursor res = mydb.getdata();
        int count = res.getCount();
        Log.d("val", String.valueOf(count));
        StringBuffer buffer2 = new StringBuffer(count);

        if (res.moveToLast()) {
            //buffer.append("Id :" + res.getString(0) + "\n");
            buffer2.append("Address :" + res.getString(0) + "\n");
            buffer2.append("LAtitude :" + res.getString(1) + "\n");
            buffer2.append("Longitude :" + res.getString(2) + "\n\n");
        }
        displayLocation("Current Location", buffer2.toString());
    }


    public void displayLocation(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
