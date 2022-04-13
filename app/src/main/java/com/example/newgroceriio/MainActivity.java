package com.example.newgroceriio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    NavigationBarView mHomeNavBar;

    TextView userFullName;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("users_data");

        userFullName = findViewById(R.id.homeUserName);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    String name = s.child("name").getValue(String.class);

                    userFullName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHomeNavBar = findViewById(R.id.homeNavBar);
        mHomeNavBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homePage:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                    case R.id.mapPage:
                        startActivity(new Intent(getApplicationContext(), NearestStoreActivity.class));
                        break;
                    case R.id.cartPage:
                        startActivity(new Intent(getApplicationContext(), ShoppingListActivity.class));
                        break;
                    case R.id.logOut:
                        Toast.makeText(MainActivity.this, "Logged out.", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        break;
                }
                return false;
            }
        });



    }


}
