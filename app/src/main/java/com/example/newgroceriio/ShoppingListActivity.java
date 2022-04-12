package com.example.newgroceriio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingListActivity extends AppCompatActivity {
    Button mShopListBackBtn, mListItemRemove, mListItemPlus, mListItemMinus, mShopListOrderBtn;
    TextView mListItemQuant, mListItemPrice, mShopListTotalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_page);

        mShopListBackBtn = findViewById(R.id.shopListBackBtn);
        mShopListOrderBtn = findViewById(R.id.shopListOrderBtn);
        mListItemRemove = findViewById(R.id.listItemRemove);
        mListItemPlus = findViewById(R.id.listItemPlus);
        mListItemMinus = findViewById(R.id.listItemMinus);
        mListItemQuant = findViewById(R.id.listItemQuant);
        mListItemPrice = findViewById(R.id.listItemPrice);
        mShopListTotalCost = findViewById(R.id.shopListTotalCost);



    }
}
