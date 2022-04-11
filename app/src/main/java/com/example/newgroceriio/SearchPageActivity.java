package com.example.newgroceriio;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SearchPageActivity extends AppCompatActivity {
    TextInputEditText searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        searchBar = findViewById(R.id.search_bar_cat);
    }
}
