package com.example.newgroceriio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingListActivity extends AppCompatActivity {
    Button mConfirmOrder, mItemRemove, mItemPlus, mItemMinus;
    TextView mTotalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_page);

        mConfirmOrder = findViewById(R.id.confirm_order_btn);
        mItemRemove = findViewById(R.id.listItemRemove);
        mItemPlus = findViewById(R.id.productPgPlus);
        mItemMinus = findViewById(R.id.productPgMinus);

        mTotalCost = findViewById(R.id.total_amaount);

    }
}
