package com.example.newgroceriio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText mRegisterNameInput, mRegisterEmailInput, mRegisterPasswordInput, mRegisterPasswordInput2;
    Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterNameInput = findViewById(R.id.registerNameInput);
        mRegisterEmailInput = findViewById(R.id.registerEmailInput);
        mRegisterPasswordInput = findViewById(R.id.registerPasswordInput);
        mRegisterPasswordInput2 = findViewById(R.id.registerPasswordInput2);
        mRegisterBtn = findViewById(R.id.regSuccessBackBtn);
    }
}