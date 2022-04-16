package com.example.newgroceriio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newgroceriio.Adapters.ProductAdapter;
import com.example.newgroceriio.Adapters.ShoppingListItemAdapter;
import com.example.newgroceriio.Models.Product;
import com.example.newgroceriio.Models.ShoppingList;
import com.example.newgroceriio.Models.ShoppingListItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity implements ShoppingListItemAdapter.OnShoppingListItemListener{
    private RecyclerView recyclerView;
    private ShoppingListItemAdapter adapter;
    private ArrayList<ShoppingListItem> shoppingListItems;
    private FirebaseAuth fAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Button mConfirmOrder, mItemRemove, mItemPlus, mItemMinus, mBackToHome;
    private TextView mTotalCost;
    private String userID;
    private ShoppingList shoppingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_page);
        fAuth = FirebaseAuth.getInstance();
        mConfirmOrder = findViewById(R.id.shopListOrderBtn);
        mItemRemove = findViewById(R.id.listItemRemove);
        mItemPlus = findViewById(R.id.productPgPlus);
        mItemMinus = findViewById(R.id.productPgMinus);
        mBackToHome = findViewById(R.id.shopListBackBtn);

        mBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CollectionLocationActivity.class));
            }
        });

        mTotalCost = findViewById(R.id.shopListTotalCost);

        // Link view widgets to objects
        recyclerView = findViewById(R.id.shopListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListItems = new ArrayList<>();

        Intent intent = getIntent();
        userID = intent.getStringExtra("uid");
        System.out.println(userID);

        // Database Ref
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("shopping_list_data");


//        createOrUpdateShoppingList(userID);
        retrieveShoppingList(userID);





    }
    private void createOrUpdateShoppingList(String userID) {
        String keyId = userID;

        Product apple = new Product();
        apple.setProductName("Apple");

        Product pear = new Product();
        pear.setProductName("Pear");

        if(shoppingList == null){
            shoppingList = new ShoppingList();
        }

        ShoppingListItem shoppingListItem1 = new ShoppingListItem();
        shoppingListItem1.setProduct(apple);
        shoppingListItem1.add1Quantity();

        ShoppingListItem shoppingListItem2 = new ShoppingListItem();
        shoppingListItem2.setProduct(apple);
        shoppingListItem2.setQuantity(3);


        shoppingList.setUserUid(userID);
        shoppingList.updateShopListItems(shoppingListItem1);
        shoppingList.updateShopListItems(shoppingListItem2);
        mDatabase.child(keyId).setValue(shoppingList);


    }

    private void retrieveShoppingList(String userID) {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    ShoppingList shoppingList = s.getValue(ShoppingList.class);
                    ArrayList<ShoppingListItem> shopItems = shoppingList.getShopListItems();
                    shoppingListItems = shopItems;
                    System.out.println(s);
                }
                loadToCardView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void loadToCardView(){
        adapter = new ShoppingListItemAdapter(this, shoppingListItems, this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onShoppingListItemClick(int position) {

    }
}
