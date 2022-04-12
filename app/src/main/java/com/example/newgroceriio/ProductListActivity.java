package com.example.newgroceriio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ProductListActivity extends AppCompatActivity {
    Button mProductCatBackBtn;
    TextView mProductCatTitleText;
    ListView mProductListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);
        mProductCatBackBtn = findViewById(R.id.productCatBackBtn);
        mProductCatTitleText = findViewById(R.id.productCatTitleText);
        mProductListView = findViewById(R.id.productListView);
    }
}
