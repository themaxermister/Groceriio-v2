package com.example.newgroceriio;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ProductListActivity extends AppCompatActivity {
    TextInputEditText searchBar;
    ImageView productImage;
    TextView productName, description, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        searchBar = findViewById(R.id.search_bar_cat);
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        price = findViewById(R.id.product_price);
        description = findViewById(R.id.product_description);

    }
}
