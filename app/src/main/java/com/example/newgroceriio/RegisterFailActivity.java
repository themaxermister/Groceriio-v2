package com.example.newgroceriio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class RegisterFailActivity extends AppCompatActivity {
    Button mRegFailSendBtn, mRegFailBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fail);

        mRegFailSendBtn = findViewById(R.id.regFailResendBtn);
        mRegFailBackBtn = findViewById(R.id.regFailBackBtn);

    }
}