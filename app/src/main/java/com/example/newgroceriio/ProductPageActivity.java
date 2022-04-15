package com.example.newgroceriio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newgroceriio.Models.StockValue;
import com.example.newgroceriio.Models.Store;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductPageActivity extends AppCompatActivity {
    private static final String TAG = "ProductPageActivity";
    private ImageView mProductPgImage;
    private Button mProductPgBackBtn, mProductPgAddToCart;
    private TextView mProductPgName, mProductPgBrand, mProductPgMetric,
            mProductPgStockVal, mProductPgPrice;

    private DatabaseReference storeRef, storeRefTwo;
    private DatabaseReference stockRef;
    private List<String> locations;

    private SharedPreferences sharedPreferences;
    private Location user_location;

    private List<LatLng> latLngList;
    private final String[] storeID = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        Log.e(TAG, "onCreate");

        mProductPgBackBtn = findViewById(R.id.productPgBackBtn);
        mProductPgAddToCart = findViewById(R.id.productPgAddToCart);

        mProductPgStockVal = findViewById(R.id.productPgStockVal);

        mProductPgName = findViewById(R.id.productPgName);
        mProductPgBrand = findViewById(R.id.productPgBrand);
        mProductPgMetric = findViewById(R.id.productPgMetric);
        mProductPgPrice = findViewById(R.id.productPgPrice);
        mProductPgImage = findViewById(R.id.productPgImage);




        //Get intent from ProductListActivity
        Intent intent = getIntent();
        String pName = intent.getStringExtra("product_name");
        String pBrand = intent.getStringExtra("product_brand");
        String pMetric = intent.getStringExtra("product_metric");
        String pPrice = intent.getStringExtra("product_price");
        String pUrl = intent.getStringExtra("product_url");
        String pId = intent.getStringExtra("product_id");

        mProductPgAddToCart = findViewById(R.id.productPgAddToCart);

        mProductPgAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(mProductPgStockVal.getText().toString())>0){
                    Intent intent = new Intent(ProductPageActivity.this, ShoppingListActivity.class);
                    intent.putExtra("product_id", pId);
                    intent.putExtra("store_id", storeID[0]);
                    intent.putExtra("product_name", pName);
                    intent.putExtra("product_url", pUrl);
                    intent.putExtra("product_price", String.valueOf(pPrice));

                    Log.e(TAG, storeID[0]);
                    startActivity(intent);

                }
            }
        });

        mProductPgName.setText(pName);
        mProductPgBrand.setText(pBrand);
        mProductPgMetric.setText(pMetric);
        mProductPgPrice.setText(pPrice);
        Glide.with(ProductPageActivity.this)
                .load(pUrl)
                .into(mProductPgImage);

        locations = new ArrayList<>();
        latLngList = new ArrayList<>();

//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_location = new Location("");
        LocationController locationController = LocationController.getInstance();
//        String latitude = locationController.latitude;
//        String longitude = sharedPreferences.getString("user_longitude","");
        user_location.setLatitude(locationController.latitude);
        user_location.setLongitude(locationController.longitude);

        storeRef = FirebaseDatabase.getInstance().getReference("store_data");
        storeRefTwo = FirebaseDatabase.getInstance().getReference("store_data");

        storeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    Store store = s.getValue(Store.class);
                    System.out.println(store.getAddress());
                    locations.add(store.getAddress());
                }
                convertAddressToLatLng(locations);
                sortMarkersFromLocation(user_location);


                storeRefTwo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot s:snapshot.getChildren()){
                            Store store_inner = s.getValue(Store.class);

                            BigDecimal lat = new BigDecimal(store_inner.getLatitude());
                            lat = lat.setScale(5, BigDecimal.ROUND_HALF_EVEN);

                            BigDecimal log = new BigDecimal(store_inner.getLongitude());
                            log = log.setScale(5, BigDecimal.ROUND_HALF_EVEN);

                            BigDecimal lat_toCompare = new BigDecimal(latLngList.get(0).latitude);
                            lat_toCompare = lat_toCompare.setScale(5, BigDecimal.ROUND_HALF_EVEN);

                            BigDecimal log_toCompare = new BigDecimal(latLngList.get(0).longitude);
                            log_toCompare = log_toCompare.setScale(5, BigDecimal.ROUND_HALF_EVEN);

                            System.out.println(lat.toString());
                            System.out.println(log.toString());
                            if(lat.doubleValue() == lat_toCompare.doubleValue() && log.doubleValue() == log_toCompare.doubleValue()){
                                storeID[0] = store_inner.getStoreId();

                            }
                        }
                        getStockQuantity(storeID[0], pId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        mProductPgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getStockQuantity(final String storeID, String pid){
        stockRef = FirebaseDatabase.getInstance().getReference("stock_data");

        stockRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()){
                    for(DataSnapshot stock : s.getChildren()){
                        System.out.println(stock);
                        StockValue sv = stock.getValue(StockValue.class);
                        if(sv.getStoreId().equals(storeID) && sv.getProductId().equals(pid)){
                            mProductPgStockVal.setText(String.valueOf(sv.getQuantityAvailable()));
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

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

        // convert object address to object latlng
        for(int i = 0; i < addresses.size(); i++){
            LatLng lat_lng = new LatLng(addresses.get(i).getLatitude(), addresses.get(i).getLongitude());
            latLngList.add(lat_lng);
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
}
