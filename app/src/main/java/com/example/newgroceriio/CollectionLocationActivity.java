package com.example.newgroceriio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


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