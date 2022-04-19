package com.example.newgroceriio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newgroceriio.Models.ShoppingList;
import com.example.newgroceriio.Models.ShoppingListItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CollectionLocationActivity extends AppCompatActivity {
    Button mCollectBackBtn, mCollectOrderConfirmBtn;
    TextView mCollectAddrText, mCollectStartAddr, mCollectEndAddr;

    // Location variables
    private List<String> locationsList;
    private Location currLocation;
    private LocationRequest locationRequest;
    public static final int REQUEST_CHECK_SETTING = 1001;
    private FusedLocationProviderClient locClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;
    private List<LatLng> latLngList;
    private String TAG = "OrderConfirmationActivity";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_location);

        mCollectBackBtn = findViewById(R.id.collectBackBtn);
        mCollectOrderConfirmBtn = findViewById(R.id.collectOrderConfirmBtn);
        mCollectAddrText = findViewById(R.id.collectAddrText);
        mCollectStartAddr = findViewById(R.id.collectStartAddr);
        mCollectEndAddr = findViewById(R.id.collectEndAddr);


        mCollectBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Previous page
                finish();
            }
        });

        mCollectOrderConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to next page
                startActivity(new Intent(CollectionLocationActivity.this, OrderConfirmationActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTING) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Toast.makeText(this, "GPS is turned on. Press the button one more time to continue", Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "GPS is required to be turned on", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private void GetLocations() {

        // Database Ref
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference().child("store_data");

        storeRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot s : snapshot.getChildren()) {
                    String address = s.child("Address").getValue(String.class);
                    System.out.println(s);
                    System.out.println(address);
                    locationsList.add(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this,
                permissions,
                LOCATION_PERMISSION_REQUEST_CODE);
    }


    private void getDeviceLocation() {

        locClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        }

        locClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Log.d(TAG, "Found location");
                Location current_location = (Location) task.getResult();
                if(current_location!= null) {
                    editor.putString("user_latitude", String.valueOf(current_location.getLatitude()));
                    editor.putString("user_longitude", String.valueOf(current_location.getLongitude()));
                    editor.apply();
                }

                currLocation.setLatitude(current_location.getLatitude());
                currLocation.setLongitude(current_location.getLongitude());
            }
        });

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
                }
            }
        }
    }

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

    public String getFullAddress(Location input){
        Geocoder geocoder = new Geocoder(CollectionLocationActivity.this,
                Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(input.getLatitude(),
                    input.getLongitude(), 1);
            String strAdd = addresses.get(0).getAddressLine(0);
            return strAdd;
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}