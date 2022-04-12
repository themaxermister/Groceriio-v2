package com.example.newgroceriio;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyEmailActivity extends AppCompatActivity{

    Button mResendBtn;
    TextView mResendText;
    FirebaseAuth fAuth;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        mResendBtn = findViewById(R.id.resendButton);
        mResendText = findViewById(R.id.textResend);

        fAuth = FirebaseAuth.getInstance();

        mResendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mResendBtn.setEnabled(false);

                // Send VERIFICATION LINK
                mHandler.postDelayed(mToastRunnable, 5000);


                fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast message below that informs user that verification email sent
                        Toast.makeText(VerifyEmailActivity.this, "Verification email has been sent.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("this is a tag?", "onFailure: Email not sent" + e.getMessage());
                    }
                });

            }
        });
    }

    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            mResendBtn.setEnabled(true);
        }
    };

}