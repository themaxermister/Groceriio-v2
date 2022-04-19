package com.example.newgroceriio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CollectionLocationActivity extends AppCompatActivity {
    Button mCollectBackBtn, mCollectOrderConfirmBtn;
    TextView mCollectAddrText, mCollectStartAddr, mCollectEndAddr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_location);

        mCollectBackBtn = findViewById(R.id.collectBackBtn);
        mCollectOrderConfirmBtn = findViewById(R.id.collectOrderConfirmBtn);
        mCollectAddrText = findViewById(R.id.collectAddrText);
        mCollectStartAddr = findViewById(R.id.collectStartAddr);
        mCollectEndAddr = findViewById(R.id.collectEndAddr);
    }
}