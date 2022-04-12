package com.example.newgroceriio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SearchPageActivity extends AppCompatActivity {
    Button mSearchBackBtn;
    TextInputEditText mSearchInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        mSearchBackBtn = findViewById(R.id.productBackBtn);
        mSearchInput = findViewById(R.id.searchBarInput);

        mSearchBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
