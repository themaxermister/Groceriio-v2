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
    private Boolean mLocationPermissionsGranted = false;
    private String TAG = "OrderConfirmationActivity";
    private String currentAddress = null;
    private String currentStoreAddress = null;

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_location);

        mCollectBackBtn = findViewById(R.id.collectBackBtn);
        mCollectOrderConfirmBtn = findViewById(R.id.collectOrderConfirmBtn);
        mCollectAddrText = findViewById(R.id.collectAddrText);
        mCollectStartAddr = findViewById(R.id.collectStartAddr);
        mCollectEndAddr = findViewById(R.id.collectEndAddr);

        locationsList = new ArrayList<>();
        currLocation = new Location("");


        mCollectBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Previous page
                finish();
            }
        });

        Intent intent = getIntent();

        currentStoreAddress = intent.getStringExtra("currentStoreAddress");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentAddress = sharedPreferences.getString("currentAddress","");

        editor = sharedPreferences.edit();

        System.out.println("Below is current address and curr store addr");
        System.out.println(currentAddress);
        System.out.println(currentStoreAddress);


        mCollectEndAddr.setText(currentAddress);
        mCollectStartAddr.setText(currentStoreAddress);
        mCollectAddrText.setText(currentStoreAddress);

        mCollectOrderConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to next page
                Intent intent = new Intent(CollectionLocationActivity.this, OrderConfirmationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}