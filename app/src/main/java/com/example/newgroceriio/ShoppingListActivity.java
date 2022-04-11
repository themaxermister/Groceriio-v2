package com.example.newgroceriio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingListActivity extends AppCompatActivity {
    Button confirmOrder, removeItem, increaseQuantity, decreaseQuantity;
    TextView totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_page);

        confirmOrder = findViewById(R.id.confirm_order_btn);
        removeItem = findViewById(R.id.remove_item_btn);
        increaseQuantity = findViewById(R.id.quantity_input_plus);
        decreaseQuantity = findViewById(R.id.quantity_input_minus);

        totalAmount = findViewById(R.id.total_amaount);

    }
}
