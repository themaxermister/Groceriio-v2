package com.example.newgroceriio.Models;


public class ShoppingListItem {
    private Product Product;
    private int Quantity;

    public ShoppingListItem() {
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