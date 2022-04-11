package com.example.newgroceriio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class OrderConfirmationActivity extends AppCompatActivity {
    Button mBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        mBackToHome = findViewById(R.id.ordSuccessBackBtn);
    }
}