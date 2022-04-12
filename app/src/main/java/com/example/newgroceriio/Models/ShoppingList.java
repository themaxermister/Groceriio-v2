package com.example.newgroceriio.Models;

import java.util.List;

public class ShoppingList {
    public String UserUid;
    public List<Product> Products;

    public ShoppingList(){}
    public ShoppingList(String userUid, List<Product> products){
        UserUid = userUid;
        Products = products;
    }

    public String getUserUid() {
        return UserUid;
    }

    public void setUserUid(String userUid) {
        UserUid = userUid;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }
}