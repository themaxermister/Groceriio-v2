package com.example.newgroceriio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ItemPageActivity extends AppCompatActivity {
    TextInputEditText searchBar;
    Button reduceQuantity, increaseQuantity;
    TextView quantityInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);
        searchBar = findViewById(R.id.searchBarInput);
        reduceQuantity = findViewById(R.id.productPgMinus);
        increaseQuantity = findViewById(R.id.productPgPlus);
        quantityInput = findViewById(R.id.productPgQuantInp);
    }
}
