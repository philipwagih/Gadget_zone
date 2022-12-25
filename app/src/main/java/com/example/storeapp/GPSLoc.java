package com.example.storeapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
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

import com.example.storeapp.databinding.ActivityGpslocBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.storeapp.databinding.ActivityGpslocBinding;

import java.io.IOException;
import java.util.List;

public class GPSLoc extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String deliveryAddress;
    private ActivityGpslocBinding binding;
    LocationManager locManager;
    myLocationListener locListener;
    Button getLocation,saveLocation;
    EditText addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGpslocBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        addressText = (EditText) findViewById(R.id.locationtext);
        getLocation = (Button) findViewById(R.id.CurrentLoc);
        saveLocation = (Button) findViewById(R.id.saveButton);
        locListener = new myLocationListener(getApplicationContext());
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, locListener);;
        }
        catch(SecurityException e)
        {
            Toast.makeText(getApplicationContext(), "Current Location is denied", Toast.LENGTH_SHORT).show();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc = null;

                try {
                    loc=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                catch (SecurityException x){
                    Toast.makeText(getApplicationContext(),"Please Allow Location Access",Toast.LENGTH_SHORT).show();
                }
                if(loc!=null)
                {
                    LatLng myPosition = new LatLng(loc.getLatitude(),loc.getLongitude());
                    try {
                        addressList = coder.getFromLocation(myPosition.latitude,myPosition.longitude,1);
                        if(!addressList.isEmpty()){
                            String address="";
                            for(int i = 0; i<=addressList.get(0).getMaxAddressLineIndex();i++)
                            {
                                address+=addressList.get(0).getAddressLine(i) + ", ";
                            }
                            mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location").snippet(address)).setDraggable(true);
                            addressText.setText(address);
                        }
                    } catch (IOException e) {
                        mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please wait until position is determined",Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryAddress=addressText.getText().toString();
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {}
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {}
            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                try {
                    addressList = coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addressList.isEmpty())
                    {
                        String address = "";
                        for(int i = 0; i<=addressList.get(0).getMaxAddressLineIndex();i++)
                        {
                            address+=addressList.get(0).getAddressLine(i) + ", ";
                        }
                        addressText.setText(address);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No address for this location",Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Check Network, can't obtain location",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}