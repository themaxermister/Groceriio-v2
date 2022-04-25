package com.example.newgroceriio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.HashMap;
import java.util.Map;

public class ShoppingListActivity extends AppCompatActivity implements ShoppingListItemAdapter.OnShoppingListItemListener{
    private RecyclerView recyclerView;
    private ShoppingListItemAdapter adapter;
    private FirebaseAuth fAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private DatabaseReference stockRef;
    private Button mConfirmOrder, mBackToHome;
    private TextView mTotalCost;
    private String userID;
    private static ShoppingList shoppingList;
    private static ArrayList<ShoppingListItem>  allItems;
    private SharedPreferences sharedPreferences;
    private String storeId;

    private String currentAddress = null;
    private String currentStoreAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_page);
        fAuth = FirebaseAuth.getInstance();
        mConfirmOrder = findViewById(R.id.shopListOrderBtn);
        mBackToHome = findViewById(R.id.shopListBackBtn);
        mTotalCost = findViewById(R.id.shopListTotalCost);

        mBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Link view widgets to objects
        recyclerView = findViewById(R.id.shopListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userID = sharedPreferences.getString("uid","");
        System.out.println("PRINT USERID");
        System.out.println(userID);

        // Database Ref
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("shopping_list_data");
        stockRef = FirebaseDatabase.getInstance().getReference("stock_data");

        //Access data from intent
        Intent intent = getIntent();
        currentAddress = intent.getStringExtra("currentAddress");
        String productId = intent.getStringExtra("product_id");
        storeId = intent.getStringExtra("store_id");
        String productName = intent.getStringExtra("product_name");
        String productUrl = intent.getStringExtra("product_url");
        String productPrice = intent.getStringExtra("product_price");
        String prevActivity = intent.getStringExtra("prev_activity");


        System.out.println("below is store Id, Shopping list act");
        System.out.println(storeId);


        if (storeId == null) {
            storeId = sharedPreferences.getString("nearestStoreId","");
        }
        retrieveStoreAddress();


        if(shoppingList == null){
            shoppingList = new ShoppingList();
            allItems = new ArrayList<>();
        }

        if(userID != null) {
            if (prevActivity != null){
                if (prevActivity.equals("order_confirmed")){
                    allItems = new ArrayList<>();
                    emptyShoppingList(userID);
                    updateShoppingList();
                }
            }
            else{
                retrieveShoppingList(userID, productId,storeId, productName, productUrl, productPrice);
            }

        }

    }

    //function to clear all items from user's shopping list
    private void emptyShoppingList(String userID){
        System.out.println("EMPTYING LIST NOW");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    if (s.getKey().equals(userID)){
                        s.getRef().removeValue();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(ShoppingListActivity.class.toString(),"Retrieved Data Cancelled");
            }
        });
    }

    //function to look for and retrieve the correct user's shopping list data from firebase
    private void retrieveShoppingList(String userID, String productId, String storeId, String productName, String productUrl, String productPrice) {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){

                    if(s.getKey().equals(userID)){  //search for user's shopping list via their userID
                        shoppingList = s.getValue(ShoppingList.class);
                    }
                }

                allItems = shoppingList.getShopListItems();
                if(productId != null && storeId != null){
                    addItemToList(productId, storeId, productName,productUrl, productPrice); //obtain necessary shopping list information
                }
                if(allItems.size() >0){
                    loadToCardView(); //call function to display shopping list to CardView
                }
                updateShoppingList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(ShoppingListActivity.class.toString(),"Retrieved Data Cancelled");
            }
        });
    }

    //function to obtain store information of relevant store from firebase
    private void retrieveStoreAddress() {
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference().child("store_data");

        storeRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot s : snapshot.getChildren()) {
                    String address = s.child("Address").getValue(String.class);
                    String newStoreId = s.child("StoreId").getValue(String.class);

                    if (newStoreId.equals(storeId)) {
                        currentStoreAddress = address;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(ShoppingListActivity.class.toString(),"Retrieved Data Cancelled");
            }
        });

        //prepare information to be passed on to CollectionLocationActivity
        mConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ShoppingListActivity.class.toString(), currentStoreAddress);
                if (currentStoreAddress != null){
                    Intent intent = new Intent(ShoppingListActivity.this, CollectionLocationActivity.class);
                    intent.putExtra("currentAddress", currentAddress);
                    intent.putExtra("currentStoreAddress", currentStoreAddress);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }


    //function to add new item to user's shopping list
    private void addItemToList(String productId, String storeId, String productName, String productUrl, String productPrice){
        ShoppingListItem shoppingItem = new ShoppingListItem();
        Product p = new Product();
        p.setProductId(productId);
        p.setProductName(productName);
        p.setImgUrl(productUrl);
        p.setPrice(Double.parseDouble(productPrice));
        shoppingItem.setProduct(p);
        shoppingItem.setStoreId(storeId);
        shoppingItem.add1Quantity();

        //check if user's shopping list is empty and add items
        if(allItems.size() == 0){
            for(ShoppingListItem i: shoppingList.getShopListItems()){
                allItems.add(i);
            }
        }

        //otherwise add 1 quantity of items already in the user's shopping list
        boolean check = false;
        ArrayList<ShoppingListItem> allItemsUpdated = new ArrayList<ShoppingListItem>();
        for(ShoppingListItem i: allItems){
            if (i.getProduct().getProductName().equals(productName)) {
                i.add1Quantity();
                check = true;
            }
            allItemsUpdated.add(i);
        }
        allItems = allItemsUpdated;

        if(!check){
            allItems.add(shoppingItem);
        }
        updateShoppingList();
    }


    private void updateShoppingList(){
        Map<String, Object> updated = new HashMap<String,Object>();
        if(adapter == null) {
            adapter = new ShoppingListItemAdapter(this, allItems, this);
        }
        shoppingList.setShopListItems(adapter.getShoppingList());
        updated.put(userID, shoppingList);
        mDatabase.updateChildren(updated);  //update firebase database
    }

    //function to display shopping list in a recycler view
    private void loadToCardView(){
        adapter = new ShoppingListItemAdapter(this, allItems, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        updateShoppingList();
    }

    @Override
    protected void onPause() {
        // call the superclass method first
        super.onPause();
        updateShoppingList();
    }

    @Override
    public void onShoppingListItemClick(int position) {

    }
}