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
        setContentView(R.layout.item_page);
        searchBar = findViewById(R.id.search_bar_cat);
        reduceQuantity = findViewById(R.id.quantity_input_minus);
        increaseQuantity = findViewById(R.id.quantity_input_plus);
        quantityInput = findViewById(R.id.quantity_input);
    }
}
