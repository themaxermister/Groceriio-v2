package com.example.newgroceriio;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.newgroceriio.Models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OrderConfirmationActivity extends AppCompatActivity {
    Button mBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mBackToHome = findViewById(R.id.ordSuccessBackBtn);
        mBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(OrderConfirmationActivity.this, ShoppingListActivity.class);
                mIntent.putExtra("prev_activity", "order_confirmed");
                mIntent.putExtra("uid", userId);
                startActivity(mIntent);
                finish();
            }
        });
    }
}