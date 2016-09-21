package com.example.varunrv.databaseexample;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


class DatabaseActivity extends AppCompatActivity {
    DBHelp mydb;
    String address,latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Log.d("val", "activity created");
        mydb = new DBHelp(DatabaseActivity.this);
        Log.d("val", "after sqladapter");
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
           }
        else
        {
            Toast.makeText(this, "GPS is not Enabled in your devide", Toast.LENGTH_SHORT).show();
           }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocManager loc;
        loc = new MyLocManager();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, loc);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        operation();
    }


    public void operation() {
        long i = mydb.insert("ccc", "44", "-122");
        String s = String.valueOf(i);
        Log.d("values", s);
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

        showMessage("currentlocation", buffer2.toString());

    }

    public void showMessage(String title, String Message) {
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
