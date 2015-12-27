package com.example.matias.anda.utilities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Matias on 19-12-2015.
 */
public class SystemUtilities {

    Context context;
    Location location;
    private LocationManager locationManager;
    boolean gpsActivo;
    private LocationListener locationListener;
    static final int MIN_TIME = 0;
    static final int MIN_DISTANCE = 0;


    /**
     * Constructor
     */
    public SystemUtilities(Context context) {
        this.context = context;
    }// SystemUtilities(Context context)

    /**
     * Método que consulta el estado de la conexión a Internet
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }// isNetworkAvailable()

    /** Método que obtiene la geolocalizacion */

    public Location getLocation() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gpsActivo = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gpsActivo) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this.context,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this.context,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    System.out.println(" NO HAY PERMISOS");
                    return null;
                }
                else
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            else
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        else {
            Toast.makeText(this.context, "EL GPS NO ESTA ACTIVADO", Toast.LENGTH_LONG).show();
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(location != null){
                    System.out.println(location.getLatitude());
                    System.out.println(location.getLongitude());

                }

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
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                MIN_TIME,
                MIN_DISTANCE,
                locationListener);

        return location;
    }// End getLocation





}


