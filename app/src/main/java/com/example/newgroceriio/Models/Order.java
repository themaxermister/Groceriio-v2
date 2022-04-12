package com.example.newgroceriio.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    public String UserUid;
    public String OrderUid;
    public LocalDateTime OrderDateTime;
    public List<Product> Products;

    public Order(){}
    public Order(String orderUid, String userUid, LocalDateTime orderDateTime, List<Product> products){
        OrderUid = orderUid;
        UserUid = userUid;
        OrderDateTime = orderDateTime;
        Products = products;
    }

    public String getUserUid() {
        return UserUid;
    }

    public void setUserUid(String userUid) {
        UserUid = userUid;
    }

    public String getOrderUid() {
        return OrderUid;
    }

    public void setOrderUid(String orderUid) {
        OrderUid = orderUid;
    }

    public LocalDateTime getOrderDateTime() {
        return OrderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        OrderDateTime = orderDateTime;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }
}
