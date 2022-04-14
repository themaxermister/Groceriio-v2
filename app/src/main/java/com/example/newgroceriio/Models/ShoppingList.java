package com.example.newgroceriio.Models;


import java.util.ArrayList;

public class ShoppingList {
    private String UserUid;
    private ArrayList<ShoppingListItem> shopListItems = new ArrayList<>();

    public ShoppingList() {
    }

    public ShoppingList(String userUid, ArrayList<ShoppingListItem> shopListItems) {
        UserUid = userUid;
        this.shopListItems = shopListItems;
    }

    public String getUserUid() {
        return UserUid;
    }

    public void setUserUid(String userUid) {
        UserUid = userUid;
    }

    public ArrayList<ShoppingListItem> getShopListItems() {
        return shopListItems;
    }

    public void setShopListItems(ArrayList<ShoppingListItem> shopListItems) {
        this.shopListItems = shopListItems;
    }

    public void updateShopListItems(ShoppingListItem shoppingListItem) {
        this.shopListItems.add(shoppingListItem);
    }
}