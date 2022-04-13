package com.example.newgroceriio;

import static android.content.Intent.EXTRA_EMAIL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newgroceriio.Adapters.CategoryAdapter;
import com.example.newgroceriio.Adapters.ProductAdapter;
import com.example.newgroceriio.Models.Category;
import com.example.newgroceriio.Models.Product;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryListener{
    NavigationBarView mHomeNavBar;

    TextView userFullName;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase, productsRef;

    private RecyclerView recyclerView;
    ArrayList<Category> categories;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Intent intent = getIntent();
        String emailFrmLogin = intent.getStringExtra("email");

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("users_data");


        userFullName = findViewById(R.id.homeUserName);

        // Retrieve User name
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    String name = s.child("name").getValue(String.class);
                    String email = s.child("email").getValue(String.class);
                    if(emailFrmLogin.equals(email)){
                        userFullName.setText("Welcome, " + name);
                        Toast.makeText(
                                MainActivity.this,
                                "Found Username",
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        productsRef = database.getReference("product_data");
        categories = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    Product product = s.getValue(Product.class);
                    Category c = new Category();
                    c.setCategoryType(product.getProductType());
                    categories.add(c);
                }
                loadToCardView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void loadToCardView(){
        adapter = new CategoryAdapter(this, categories, this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }



    @Override
    public void onCategoryClick(int position) {
        Category c = categories.get(position);

        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);

        intent.putExtra("type", c.getCategoryType());
        startActivity(intent);

    }
}
