package com.example.harsharora.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    String provider;
    TextView latv;
    TextView lngv;
    TextView speedv;
    TextView accuracyv;
    TextView bearingv;
    TextView addressv, altitudev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        latv = (TextView) findViewById(R.id.lat);
        lngv = (TextView) findViewById(R.id.lng);
        accuracyv = (TextView) findViewById(R.id.accuracy);
        speedv = (TextView) findViewById(R.id.speed);
        bearingv = (TextView) findViewById(R.id.bearing);
        addressv = (TextView) findViewById(R.id.address);
        altitudev = (TextView) findViewById(R.id.altitude);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        onLocationChanged(location);




    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        Double alt=location.getAltitude();
        float bearing=location.getBearing();
        float speed=location.getSpeed();
        float accuracy=location.getAccuracy();
        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lat,lng,1);
            if (listAddresses!=null && listAddresses.size()>0)
            {
                Log.i("Place Info",listAddresses.get(0).toString());
                String addressholder="";
               for(int i=0;i<=listAddresses.get(0).getMaxAddressLineIndex();i++)
                {
                 addressholder +=   listAddresses.get(0).getAddressLine(i)+"\n";
                }
                addressv.setText("Address:\n"+addressholder);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

       // Log.i("Latitude", lat.toString());
         latv.setText("Latitude: "+ lat.toString());
       // Log.i("Longitude", lng.toString());
         lngv.setText("Longitude"+lng.toString());
         altitudev.setText("Altitude:"+alt.toString()+"m");
         speedv.setText("Speed"+String.valueOf(speed)+"m/s");
         accuracyv.setText("Accuracy:"+String.valueOf((accuracy))+"m");
         bearingv.setText("Bearing: "+String.valueOf(bearing));




         //Log.i("Altitude", alt.toString());

        /*Log.i("Bearing", String.valueOf(bearing));
        Log.i("Speed", String.valueOf(speed));
        Log.i("Accuracy", String.valueOf(accuracy));*/



    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
}
