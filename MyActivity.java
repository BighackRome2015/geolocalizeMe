package com.example.geolocalizzamI;

import android.app.Activity;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private String providerId=LocationManager.GPS_PROVIDER;
    private Geocoder geo=null;
    private LocationManager locationManager=null;
    private static final int MIN_DIST=20;
    private static final int MIN_PERIOD=30000;
    public Location location=null;


    private LocationListener locationListener = new LocationListener()
    {


    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    @Override

    protected void onResume()
    {
        super.onResume();
        geo=new Geocoder(this, Locale.getDefault());
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location  = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location!=null)
            updateGUI(location);
        if (locationManager!=null && locationManager.isProviderEnabled(providerId))
            updateText(R.id.enabled, "TRUE");
        else
            updateText(R.id.enabled, "FALSE");
        locationManager.requestLocationUpdates(providerId, MIN_PERIOD,MIN_DIST, locationListener);
    }



    @Override
    protected void onPause()
    {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    private LocationListener locationListener = new LocationListener()
    {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
        @Override
        public void onProviderEnabled(String provider)
        {
            // attivo GPS su dispositivo
            updateText(R.id.enabled, "TRUE");
        }
        @Override
        public void onProviderDisabled(String provider)
        {
            // disattivo GPS su dispositivo
            updateText(R.id.enabled, "FALSE");
        }
        @Override
        public void onLocationChanged(Location location)
        {
            updateGUI(location);
        }
    };
}
