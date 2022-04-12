package com.example.newgroceriio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText mRegisterNameInput, mRegisterEmailInput, mRegisterPasswordInput, mRegisterPasswordInput2;
    Button mRegisterBtn, mRegisterBackBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterNameInput = findViewById(R.id.registerNameInput);
        mRegisterEmailInput = findViewById(R.id.registerEmailInput);
        mRegisterPasswordInput = findViewById(R.id.registerPasswordInput);
        mRegisterPasswordInput2 = findViewById(R.id.registerPasswordInput2);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mRegisterBackBtn = findViewById(R.id.registerBackBtn);

        fAuth = FirebaseAuth.getInstance();
        Handler handler = new Handler();

        mRegisterBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mRegisterEmailInput.getText().toString().trim();
                String password1 = mRegisterPasswordInput.getText().toString().trim();
                String password2 = mRegisterPasswordInput2.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mRegisterEmailInput.setError("Email required.");
                    return;
                }

                if (TextUtils.isEmpty(password1)){
                    mRegisterPasswordInput.setError("Password required.");
                    return;
                }

                if (TextUtils.isEmpty(password2)){
                    mRegisterPasswordInput2.setError("Please re-enter password.");
                    return;
                }

                if (password1.length() < 6) {
                    mRegisterPasswordInput.setError("Password needs to be at least 6 characters.");
                    return;
                }

                if (!(password1.equals(password2))) {
                    mRegisterPasswordInput2.setError("Passwords do not match. Please re-enter your password.");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    "User Created",
                                    Toast.LENGTH_SHORT)
                                    .show();

                            startActivity(new Intent(getApplicationContext(), RegisterSuccessActivity.class));
                        }
                        else {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    "Error! " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

            }
        });
    }
}