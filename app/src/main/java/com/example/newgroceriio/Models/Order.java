package com.example.newgroceriio.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String UserUid;
    private String OrderUid;
    private LocalDateTime OrderDateTime;
    private List<Product> OrderProducts;

    public Order(){}

    public Order(String userUid, String orderUid, LocalDateTime orderDateTime, List<Product> orderProducts) {
        UserUid = userUid;
        OrderUid = orderUid;
        OrderDateTime = orderDateTime;
        OrderProducts = orderProducts;
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

    public List<Product> getOrderProducts() {
        return OrderProducts;
    }

    public void setOrderProducts(List<Product> orderProducts) {
        OrderProducts = orderProducts;
    }
}
