package com.androidbelieve.materialnavigationdrawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {



    GPSTracker gps;
    private GoogleMap mMap;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        /// If Users GPS is off

        // create class object
        gps = new GPSTracker(MapsActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

             latitude = gps.getLatitude();
             longitude = gps.getLongitude();


            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings


            gps.showSettingsAlert();
        }
    }
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        //Read the stops from the CSV file  =========EDITED only  in speciefiec route sapir--bash   bash--sapir
        InputStream inputStream = getResources().openRawResource(R.raw.stops_south);
        CSVFile csvFile = new CSVFile(inputStream);
        List scoreList = csvFile.read();
        String [] a;
        final String [] stop=new String[scoreList.size()];
        final String [] stopInfo=new String[scoreList.size()];
        final String [] stopCode=new String[scoreList.size()];


        //We have 3 arrays,stop_number,stop_info,stop_code

        for(int i=0;i<scoreList.size();i++){
            a= (String[]) scoreList.get(i);

            stopCode[i]=a[2];
            stop[i]=a[4];
            stopInfo[i]=a[5];


        }

        // Add a marker
        LatLng mylocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(mylocation).title("המיקום שלי"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

        // Mark the stops on our Route
        for(int i=1;i<stopCode.length;i++){

            float lat = Float.parseFloat(stop[i]);
            float lon = Float.parseFloat(stopInfo[i]);

                LatLng mylocation2 = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(mylocation2).title(stopCode[i]));




        }





    }
}










