package com.example.newgroceriio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mLoginEmailInput, mLoginPasswordInput;
    Button mLoginBtn, mLoginRegisterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginEmailInput = findViewById(R.id.loginEmailInput);
        mLoginEmailInput = findViewById(R.id.loginPasswordInput);
        mLoginBtn = findViewById(R.id.loginBtn);
        mLoginRegisterBtn = findViewById(R.id.loginRegisterBtn);
    }
}