package com.example.e4trely;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText addressText;
    LocationManager locmanager;
    MyLocationListener locListener;
    Button getLoc;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        addressText=(EditText)findViewById(R.id.Loc);
        getLoc=(Button)findViewById(R.id.CurrentLoc);
        locListener=new MyLocationListener(getApplicationContext());
        locmanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,0,locListener);
        }catch (SecurityException se){
            Toast.makeText(getApplicationContext(),"You're not allowed to access the current location",Toast.LENGTH_SHORT).show();
        }
        Button done=(Button)findViewById(R.id.Return);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MapsActivity.this,MainActivity3.class);
                i.putExtra("Address",addressText.getText().toString());
                startActivity(i);
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));
        getLoc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address>addressList;
                Location Loc=null;
                try {
                    Loc=locmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                }catch (SecurityException se){
                    Toast.makeText(getApplicationContext(),"You're not allowed to access the current location",Toast.LENGTH_SHORT).show();
                }
                if(Loc!=null){
                    LatLng myposition=new LatLng(Loc.getLatitude(),Loc.getLongitude());
                    try {
                        addressList=coder.getFromLocation(myposition.latitude,myposition.longitude,1);
                        if(!addressList.isEmpty()){
                            String address="";
                            for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++){
                                address+=addressList.get(0).getAddressLine(i)+",";
                            }
                            mMap.addMarker(new MarkerOptions().position(myposition).title("My Location").snippet(address)).setDraggable(true);
                            addressText.setText(address);
                        }

                    }catch (IOException ie){
                            mMap.addMarker(new MarkerOptions().position(myposition).title("My Location"));


                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition,15));
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please wait until your position is determined",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Geocoder c=new Geocoder(getApplicationContext());
                List<Address>list;
                try {
                    list=c.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!list.isEmpty()){
                        String address="";
                        for(int i=0;i<=list.get(0).getMaxAddressLineIndex();i++){
                            address+=list.get(0).getAddressLine(i)+",";
                        }
                        addressText.setText(address);

                    }
                }catch (IOException e){
                    Toast.makeText(getApplicationContext(),"Check your network",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}