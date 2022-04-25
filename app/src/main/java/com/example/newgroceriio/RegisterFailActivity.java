package com.example.newgroceriio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFailActivity extends AppCompatActivity {
    Button mRegFailSendBtn, mRegFailBackBtn;
    FirebaseAuth fAuth;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fail);

        mRegFailSendBtn = findViewById(R.id.regFailResendBtn);
        mRegFailBackBtn = findViewById(R.id.regFailBackBtn);
        fAuth = FirebaseAuth.getInstance();

        mRegFailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterFailActivity.this, LoginActivity.class));
            }
        });

        mRegFailSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegFailSendBtn.setEnabled(false);

                // Send VERIFICATION LINK
                mHandler.postDelayed(mToastRunnable, 5000);

                fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast message below that informs user that verification email sent
                        Toast.makeText(RegisterFailActivity.this, "Verification email has been sent.", Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(RegisterFailActivity.class.toString(), "onFailure: Email not sent" + e.getMessage());
                    }
                });
            }
        });

    }
    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            mRegFailSendBtn.setEnabled(true);
        }
    };
}