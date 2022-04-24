package com.example.newgroceriio;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

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
                Intent mIntent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
                mIntent.putExtra("prev_activity", "order_confirmed");
                mIntent.putExtra("uid", userId);
                startActivity(mIntent);
                finish();
            }
        });
    }
}