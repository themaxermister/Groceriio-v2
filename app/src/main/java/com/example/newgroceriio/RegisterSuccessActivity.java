package com.example.newgroceriio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class RegisterSuccessActivity extends AppCompatActivity {
    Button mOrdSuccessBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        mOrdSuccessBackBtn = findViewById(R.id.ordSuccessBackBtn);

    }
}