package com.example.newgroceriio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProductPageActivity extends AppCompatActivity {
    ImageView mProductPgImage;
    Button mProductPgBackBtn, mProductPgMinus, mProductPgPlus, mProductPgAddToCart;
    TextView mProductPgName, mProductPgBrand, mProductPgMetric,
            mProductPgStockVal, mProductPgQuantInp, mProductPgPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        mProductPgBackBtn = findViewById(R.id.productPgBackBtn);
        mProductPgMinus = findViewById(R.id.productPgMinus);
        mProductPgPlus = findViewById(R.id.productPgPlus);
        mProductPgAddToCart = findViewById(R.id.productPgAddToCart);

        mProductPgStockVal = findViewById(R.id.productPgStockVal);
        mProductPgQuantInp = findViewById(R.id.productPgQuantInp);

        mProductPgName = findViewById(R.id.productPgName);
        mProductPgBrand = findViewById(R.id.productPgBrand);
        mProductPgMetric = findViewById(R.id.productPgMetric);
        mProductPgPrice = findViewById(R.id.productPgPrice);
        mProductPgImage = findViewById(R.id.productPgImage);


        //Get intent from ProductListActivity
        Intent intent = getIntent();
        String pName = intent.getStringExtra("product_name");
        String pBrand = intent.getStringExtra("product_brand");
        String pMetric = intent.getStringExtra("product_metric");
        String pPrice = intent.getStringExtra("product_price");
        String pUrl = intent.getStringExtra("product_url");

        mProductPgName.setText(pName);
        mProductPgBrand.setText(pBrand);
        mProductPgMetric.setText(pMetric);
        mProductPgPrice.setText(pPrice);
        Glide.with(ProductPageActivity.this)
                .load(pUrl)
                .into(mProductPgImage);




        mProductPgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
