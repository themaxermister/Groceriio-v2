package com.example.newgroceriio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ProductPageActivity extends AppCompatActivity {
    ImageView mProductPgImage;
    Button mProductPgBackBtn, mProductPgMinus, mProductPgPlus, mProductPgAddToCart;
    TextView mProductPgName, mProductPgBrand, mProductPgMetric,
            mProductPgStockVal, mProductPgQuantInp, mProductPgPrice,
            mProductPgDetailInfo, mProductPgNutriInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        mProductPgBackBtn = findViewById(R.id.productPgBackBtn);
        mProductPgMinus = findViewById(R.id.productPgMinus);
        mProductPgPlus = findViewById(R.id.productPgPlus);
        mProductPgAddToCart = findViewById(R.id.productPgAddToCart);
        mProductPgName = findViewById(R.id.productPgName);
        mProductPgBrand = findViewById(R.id.productPgBrand);
        mProductPgMetric = findViewById(R.id.productPgMetric);
        mProductPgStockVal = findViewById(R.id.productPgStockVal);
        mProductPgQuantInp = findViewById(R.id.productPgQuantInp);
        mProductPgPrice = findViewById(R.id.productPgPrice);
        mProductPgDetailInfo = findViewById(R.id.productPgDetailInfo);
        mProductPgNutriInfo = findViewById(R.id.productPgNutriInfo);

        mProductPgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
