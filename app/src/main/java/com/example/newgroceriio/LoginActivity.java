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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mLoginEmailInput, mLoginPasswordInput;
    private Button mLoginBtn, mLoginRegisterBtn;

    /*
    mLoginMsg = findViewById(R.id.loginAccessMsg);
    mLoginMsg.setVisibility(View.GONE);
    mLoginMsg.setText("");
     */
    private Handler handler = new Handler();

    private FirebaseAuth fAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

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
        fAuth = FirebaseAuth.getInstance();

        /*if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }*/

        final String[] name = new String[1];
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


                        if(fAuth.getCurrentUser() == null){
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Please register an account",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                        else{
                            if (task.isSuccessful() && fAuth.getCurrentUser().isEmailVerified()) {

                                Toast.makeText(
                                        LoginActivity.this,
                                        "User logged in successfully",
                                        Toast.LENGTH_SHORT)
                                        .show();

                                fAuth = FirebaseAuth.getInstance();
                                database = FirebaseDatabase.getInstance();
                                mDatabase = database.getReference("users_data");

                                String uid = fAuth.getCurrentUser().getUid();

                                // Retrieve User name
                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot s: snapshot.getChildren()){
                                            String email = s.child("email").getValue(String.class);
                                            if(email.equals(fAuth.getCurrentUser().getEmail())){
                                                name[0] = s.child("name").getValue(String.class);
                                            }
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("uid", uid);
                                            intent.putExtra("name", name[0]);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                            }
                            else if (task.isSuccessful() && !fAuth.getCurrentUser().isEmailVerified() ){

                                Toast.makeText(
                                        LoginActivity.this,
                                        "User email not verified",
                                        Toast.LENGTH_SHORT)
                                        .show();

                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        startActivity(new Intent(LoginActivity.this, RegisterFailActivity.class));
                                    }
                                }, 1000);   //3 seconds
                            }
                            else {
                                Toast.makeText(
                                        LoginActivity.this,
                                        "Error! " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
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