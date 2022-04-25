package com.example.newgroceriio.Models;

// each object of ShoppingListItem class contains an object of class Product as well as its quantity and the storeID of which store has it
// each ShoppingListItem object represents one item in a user's shopping list
public class ShoppingListItem {
    private Product Product;
    private int Quantity;
    private String StoreId;

    public ShoppingListItem() {
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
        Product = product;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void add1Quantity() {
        Quantity += 1;
    }

    public void remove1Quantity() {
        Quantity -= 1;
    }

}