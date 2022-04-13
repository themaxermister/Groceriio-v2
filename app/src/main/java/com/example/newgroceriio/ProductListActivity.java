package com.example.newgroceriio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newgroceriio.Adapters.ProductAdapter;
import com.example.newgroceriio.Models.Product;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductAdapter.OnProductListener{
    Button mProductBackBtn;
    TextView mProductTitleText;
    RecyclerView mProductRecyclerView;

    DatabaseReference ref;
    DatabaseReference productsRef;
    ArrayList<Product> productList;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);
        mProductBackBtn = findViewById(R.id.productListBackBtn);
        mProductTitleText = findViewById(R.id.productListTitleText);
        mProductRecyclerView = findViewById(R.id.productRecyclerView);
        recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        mProductTitleText.setText(type);
        productList = new ArrayList<>();

        // Database Ref
        ref = FirebaseDatabase.getInstance().getReference();
        productsRef = ref.child("product_data");

        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    String type_DB = s.child("ProductType").getValue(String.class);
                    if (type.equals(type_DB)) {
                        Product product = s.getValue(Product.class);
                        productList.add(product);
                    }
                    System.out.println(s);
                    loadToCardView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mProductBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadToCardView(){
        adapter = new ProductAdapter(this, productList, this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProductClick(int position) {

    }
}
