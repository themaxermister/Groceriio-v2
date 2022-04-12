package com.example.newgroceriio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class NearestStoreActivity extends AppCompatActivity {
    Button mMapSearchBackBtn;
    TextInputEditText mMapSearchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_store);

        mMapSearchBackBtn = findViewById(R.id.mapSearchBackBtn);
        mMapSearchBar = findViewById(R.id.mapSearchBarInput);
    }
}