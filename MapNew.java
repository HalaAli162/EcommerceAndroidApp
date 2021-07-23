package com.example.e4trely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MapNew extends AppCompatActivity {

    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
   // private GoogleMap mMap;
    EditText addressText;
    LocationManager locmanager;
    MyLocationListener locListener;
    Button getLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_new);
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
                Intent i=new Intent(MapNew.this,MainActivity3.class);
                i.putExtra("Address",addressText.getText().toString());
                startActivity(i);
            }
        });
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MapNew.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
        else {
            ActivityCompat.requestPermissions(MapNew.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions options=new MarkerOptions().position(latLng).title("I'm Here.");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            googleMap.addMarker(options);
                            ////////////////////////////////////-------------------/////////////////////////////////
                            getLoc.setOnClickListener(new View.OnClickListener() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onClick(View v) {
//                                    mMap.clear();
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
                                               // mMap.addMarker(new MarkerOptions().position(myposition).title("My Location").snippet(address)).setDraggable(true);
                                                addressText.setText(address);
                                            }

                                        }catch (IOException ie){
                                           // mMap.addMarker(new MarkerOptions().position(myposition).title("My Location"));


                                        }
                                      //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition,15));
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Please wait until your position is determined",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            ///////////////////////////////////-------------------//////////////////////////////////

                        }
                    });
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==44){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }
}