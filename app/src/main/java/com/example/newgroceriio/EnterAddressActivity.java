package com.example.newgroceriio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class EnterAddressActivity extends AppCompatActivity {
    Button mEnterAddrBackBtn;
    TextInputEditText mEnterAddrInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_address);

        mEnterAddrBackBtn = findViewById(R.id.enterAddrBackBtn);
        mEnterAddrInput = findViewById(R.id.enterAddrInput);
    }
}