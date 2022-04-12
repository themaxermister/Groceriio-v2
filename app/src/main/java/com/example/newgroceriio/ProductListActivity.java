package com.example.newgroceriio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ProductListActivity extends AppCompatActivity {
    Button mProductBackBtn;
    TextView mProductTitleText;
    ListView mProductListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);
        mProductBackBtn = findViewById(R.id.productListBackBtn);
        mProductTitleText = findViewById(R.id.productListTitleText);
        mProductListView = findViewById(R.id.productListView);

        mProductBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
