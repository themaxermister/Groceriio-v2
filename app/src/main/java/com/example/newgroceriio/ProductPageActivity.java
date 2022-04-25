package com.example.newgroceriio;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductPageActivity extends AppCompatActivity {
    private ImageView mProductPgImage;
    private Button mProductPgBackBtn, mProductPgAddToCart;
    private TextView mProductPgName, mProductPgBrand, mProductPgMetric,
            mProductPgStockVal, mProductPgPrice;

    private DatabaseReference stockRef;
    private List<String> locations;

    private SharedPreferences sharedPreferences;
    private Location user_location;
    private String latitude;
    private String longitude, nearestStoreId;

    private List<LatLng> latLngList;
    private final String[] storeID = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_location = new Location("");
        latitude = sharedPreferences.getString("user_latitude","");
        longitude = sharedPreferences.getString("user_longitude","");
        user_location.setLatitude(Double.parseDouble(latitude));
        user_location.setLongitude(Double.parseDouble(longitude));

        // nearestStoreId is put into sharedPreferences in MainActivity
        nearestStoreId = sharedPreferences.getString("nearestStoreId", "");
        getStockQuantity(nearestStoreId, pId);

        mProductPgAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mProductPgStockVal.getText().toString().equals("")){
                    if(Integer.parseInt(mProductPgStockVal.getText().toString())>0){
                        Intent intent = new Intent(ProductPageActivity.this, ShoppingListActivity.class);
                        intent.putExtra("product_id", pId);
                        intent.putExtra("store_id", nearestStoreId);
                        intent.putExtra("product_name", pName);
                        intent.putExtra("product_url", pUrl);
                        intent.putExtra("product_price", String.valueOf(pPrice));
                        mProductPgAddToCart.setEnabled(false);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(
                                ProductPageActivity.this,
                                "No Stock Left",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
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
                        if(sv.getStoreStockId().equals(storeID) && sv.getProductStockId().equals(pid)){
                            mProductPgStockVal.setText(String.valueOf(sv.getQuantityAvailable()));
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(ProductPageActivity.class.toString(),"Retrieved Data Cancelled");
            }
        });
    }
}