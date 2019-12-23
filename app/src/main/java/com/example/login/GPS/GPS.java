package com.example.login.GPS;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.login.R;


public class GPS  {
    public LocationManager locationManager;
    private Context context;
    private Activity activity;
    private static  final int REQUEST_LOCATION=1;
    private double latitude,longitude;

    TextView tvError;
    public GPS(Context context, Activity activity, TextView tvError){
        this.context = context;
        this.activity = activity;
        this.tvError = tvError;
        this.longitude =0.0;
        this.latitude =0.0;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }

    public void getLocation() {
        //Check Permissions again
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions( activity ,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (LocationGps !=null)
            {
                this.latitude = LocationGps.getLatitude();
                this.longitude = LocationGps.getLongitude();
//                Toast.makeText(context, "Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude, Toast.LENGTH_SHORT).show();

            }
            else if (LocationNetwork !=null)
            {
                this.latitude = LocationNetwork.getLatitude();
                this.longitude = LocationNetwork.getLongitude();
//                Toast.makeText(context, "Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude, Toast.LENGTH_SHORT).show();
            }
            else if (LocationPassive !=null)
            {
                this.latitude = LocationPassive.getLatitude();
                this.longitude = LocationPassive.getLongitude();
//                Toast.makeText(context, "Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }
            //Thats All Run Your App


        }
    }

    public void OnGPS() {
        final Handler threadHandler = new Handler(Looper.getMainLooper());
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvError.setText("");
                    }
                });
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvError.setText(R.string.gps_error);
                    }
                });
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
