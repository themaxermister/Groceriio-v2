package com.example.newgroceriio.Models;


import java.util.ArrayList;

public class ShoppingList {
    private String userUid;
    private ArrayList<ShoppingListItem> shopListItems = new ArrayList<>();

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public ArrayList<ShoppingListItem> getShopListItems() {
        return shopListItems;
    }

    public void setShopListItems(ArrayList<ShoppingListItem> shopListItems) {
        this.shopListItems = shopListItems;
    }
}