package com.example.newgroceriio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mLoginEmailInput, mLoginPasswordInput;
    Button mLoginBtn, mLoginRegisterBtn;

    /*
    mLoginMsg = findViewById(R.id.loginAccessMsg);
    mLoginMsg.setVisibility(View.GONE);
    mLoginMsg.setText("");
     */
    Handler handler = new Handler();

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        //finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginEmailInput = findViewById(R.id.loginEmailInput);
        mLoginPasswordInput = findViewById(R.id.loginPasswordInput);
        mLoginBtn = findViewById(R.id.loginBtn);
        mLoginRegisterBtn = findViewById(R.id.loginRegisterBtn);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        /*if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }*/

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mLoginEmailInput.getText().toString().trim();
                String password = mLoginPasswordInput.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mLoginEmailInput.setError("Email required.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mLoginPasswordInput.setError("Password required.");
                    return;
                }

                // mLoginMsg.setVisibility(view.GONE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(
                                    LoginActivity.this,
                                    "User logged in successfully",
                                    Toast.LENGTH_SHORT)
                                    .show();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {
                            // mLoginMsg.setText("Unsuccessful log in. Please try again.");
                            // mLoginMsg.setVisibility(view.VISIBLE);

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Error! " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
            }
        });

        mLoginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}