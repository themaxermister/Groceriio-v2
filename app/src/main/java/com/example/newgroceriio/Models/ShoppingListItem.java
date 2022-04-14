package com.example.newgroceriio.Models;


public class ShoppingListItem {
    private Product Product;
    private int Quantity;

    public ShoppingListItem() {
    }

    public com.example.newgroceriio.Models.Product getProduct() {
        return Product;
    }

    public void setProduct(com.example.newgroceriio.Models.Product product) {
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

//    public void updateShopListItem(ShoppingListItem shoppingListItem) {
//        boolean ItemPresent = shopListItems.contains(shoppingListItem);
//
//        if (ItemPresent) {
//            shoppingListItem.add1Quantity();
//        }
//
//        else {
//            shopListItems.add(shoppingListItem);
//        }
//    }
}