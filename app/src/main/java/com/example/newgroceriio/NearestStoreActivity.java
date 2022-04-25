package com.example.newgroceriio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearestStoreActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private EditText searchText;
    private Button mMapSearchBackBtn;

    //Variables
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient locClient;
    private GoogleMap mMap;

    private List<String> locationsList;
    private List<LatLng> latLngList;
    private Address search_Address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_store);

        latLngList = new ArrayList<>();

        getLocationPermission();

        Intent intent = getIntent();

        locationsList = intent.getStringArrayListExtra("locations");
        convertAddressToLatLng(locationsList);

        searchText = findViewById(R.id.mapSearchBarInput);
        mMapSearchBackBtn = findViewById(R.id.mapSearchBackBtn);

        mMapSearchBackBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Search for desired current location
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN ||
                        keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
                    geoLocate();
                }
                return false;
            }
        });



    }


    // Sort the stores based on the location searched by the user
    private void geoLocate(){
        String searchString = searchText.getText().toString();

        Geocoder geocoder = new Geocoder(NearestStoreActivity.this);

        List<Address> list = new ArrayList<>();

        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }
        catch(IOException e){
            Log.e(TAG, "IOException:" + e.getMessage());
        }

        if(list.size() > 0){
            System.out.println(list.get(0).toString());
            search_Address = list.get(0);
            moveCamera(new LatLng(search_Address.getLatitude(), search_Address.getLongitude()), 15f);

            Location search_Location = new Location("");
            search_Location.setLatitude(search_Address.getLatitude());
            search_Location.setLongitude(search_Address.getLongitude());
            // Sort store locations based on search_Location
            sortMarkersFromLocation(search_Location);

            mMap.clear();
            //Create markers to indicate all store locations
            for(int i = 0; i < latLngList.size(); i++){
                mMap.addMarker(new MarkerOptions().position(latLngList.get(i)).title(String.valueOf(i)));
            }
        }
    }

    // Get permission from phone to use the location
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this,
                permissions,
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    // Initialize the map
    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(NearestStoreActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getDeviceLocation();
            mMap.setMyLocationEnabled(true);
        }
    }


    // Convert address to latitude and longitude
    private void convertAddressToLatLng(List<String> locations){

        List<Address> addresses = new ArrayList<>();

        // Convert String address to object address
        for(String address: locations) {
            final Geocoder geocoder = new Geocoder(this);

            try {
                List<Address> tempAddresses = geocoder.getFromLocationName(address, 1);
                if (tempAddresses != null && !tempAddresses.isEmpty()) {
                    Address temp = tempAddresses.get(0);
                    // Get all product addresses
                    System.out.println(address);
                    addresses.add(temp);
                    String message = String.format("Latitude: %f, Longitude: %f", temp.getLatitude(), temp.getLongitude());
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                } else {
                    // Display appropriate message when Geocoder services are not available
                    Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Convert object Address to object LatLng
        for(int i = 0; i < addresses.size(); i++){
            LatLng lat_lng = new LatLng(addresses.get(i).getLatitude(), addresses.get(i).getLongitude());
            latLngList.add(lat_lng);
        }
    }

    // Get current device location
    private void getDeviceLocation(){
        locClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){
                @SuppressLint("MissingPermission") Task location = locClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "Found location");

                            Location current_location = (Location)task.getResult();
                            moveCamera(new LatLng(current_location.getLatitude(), current_location.getLongitude()), 15f);

                            // Sort store locations based on current_location
                            sortMarkersFromLocation(current_location);

                            //Create markers to indicate all store locations
                            for(int i = 0; i < latLngList.size(); i++){
                                mMap.addMarker(new MarkerOptions().position(latLngList.get(i)).title(String.valueOf(i)));
                            }

                        }
                        else{
                            Log.d(TAG, "Cannot find location");
                            Toast.makeText(NearestStoreActivity.this, "Location Not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    // move the camera to the desired lat and long
    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


    // Sort store locations based on current_location
    private void sortMarkersFromLocation(Location current_location) {
        System.out.println("Below is markers");

        List<Location> markers = new ArrayList<>();

        for(LatLng lat_lng: latLngList){
            Location location = new Location("");
            location.setLatitude(lat_lng.latitude);
            location.setLongitude(lat_lng.longitude);
            markers.add(location);
        }

        System.out.println(markers);
        Collections.sort(markers, new SortDistance(){
            @Override
            public int compare(Location o1, Location o2) {
                Float dist1 = o1.distanceTo(current_location);
                Float dist2 = o2.distanceTo(current_location);
                return dist1.compareTo(dist2);
            }
        });
        System.out.println(markers);

        latLngList = new ArrayList<>();
        for(Location m: markers){
            latLngList.add(new LatLng(m.getLatitude(), m.getLongitude()));
        }

    }

}

class SortDistance implements Comparator<Location> {

    public int compare(Location o1, Location o2) {
        return 0;
    }
}