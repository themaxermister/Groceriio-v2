package com.example.newgroceriio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newgroceriio.Adapters.CategoryAdapter;
import com.example.newgroceriio.Models.Category;
import com.example.newgroceriio.Models.Product;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryListener {
    private NavigationBarView mHomeNavBar;

    private TextView userFullName;
    private DatabaseReference productsRef;

    private RecyclerView recyclerView;
    private HashSet<String> categoryTypes;
    private ArrayList<Category> categories;
    private CategoryAdapter adapter;

    private DatabaseReference storeRef;
    private List<String> locationsList;
    private LocationRequest locationRequest;
    public static final int REQUEST_CHECK_SETTING = 1001;
    private static boolean mLocationBool = false;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private FusedLocationProviderClient locClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate MainActivity");
        setContentView(R.layout.activity_main);

        getLocationPermission();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        // getDeviceLocation();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String uid = intent.getStringExtra("uid");

        mHomeNavBar = findViewById(R.id.homeNavBar);
        mHomeNavBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homePage:
                        break;
                    case R.id.mapPage:
                        locationsList = new ArrayList<>();
                        GetLocations();
                        startActivityPage();
                        break;
                    case R.id.cartPage:
                        Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                        break;
                    case R.id.logOut:
                        Toast.makeText(MainActivity.this, "Logged out.", Toast.LENGTH_SHORT).show();
                        editor.clear();
                        editor.apply();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        break;
                }
                return false;
            }
        });


        editor.putString("name", name);
        editor.putString("uid", uid);
        editor.apply();

        // Store to preference instead of depending on intent from login activity
        String stored_name = sharedPreferences.getString("name", "");

        userFullName = findViewById(R.id.homeUserName);
        userFullName.setText("Welcome, " + stored_name);


        productsRef = FirebaseDatabase.getInstance().getReference("product_data");
        categories = new ArrayList<>();
        categoryTypes = new HashSet<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    Product product = s.getValue(Product.class);
                    categoryTypes.add(product.getProductType());

                }
                loadToCardView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    getDeviceLocation();
                }
            }
        }
    }

    private void loadToCardView() {
        for (String type : categoryTypes) {
            Category c = new Category();
            c.setCategoryType(type);
            categories.add(c);
        }
        adapter = new CategoryAdapter(this, categories, this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCategoryClick(int position) {
        Category c = categories.get(position);

        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
        intent.putExtra("type", c.getCategoryType());
        startActivity(intent);

    }

    ////////////// For Map Below vv //////////////


    private void startActivityPage() {
        Intent intent = new Intent(MainActivity.this, NearestStoreActivity.class);
        intent.putStringArrayListExtra("locations", (ArrayList<String>) locationsList);

        System.out.println(mLocationBool);

        new Timer().schedule(new TimerTask() {
            public void run() {
                startActivity(intent);
            }
        }, 3000);


    }



    private void GetLocations() {

        // Database Ref
        storeRef = FirebaseDatabase.getInstance().getReference().child("store_data");

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

    private void getLocationPermission() {
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
            return;
        }
        locClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location current_location = (Location) task.getResult();
                LocationController.getInstance().setLongLat(current_location.getLongitude(), current_location.getLatitude());

//                editor.putString("user_latitude", String.valueOf(current_location.getLatitude()));
//                editor.putString("user_longitude", String.valueOf(current_location.getLongitude()));
//                editor.apply();
            }
        });

    }
}
