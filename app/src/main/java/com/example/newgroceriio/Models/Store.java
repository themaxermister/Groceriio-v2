package com.example.newgroceriio.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Store {

    public String StoreId;
    public String StoreName;
    public String StoreType;
    public String Country;
    public String Location;
    public String Address;
    public String OpenTime;
    public String CloseTime;
    public List<Product> Products;

    public Store() {}

    public Store(String storeId, String storeName, String storeType, String country,
                 String location, String address, String openTime, String closeTime, List<Product> products){

        this.StoreId = storeId;
        this.StoreName = storeName;
        this.StoreType = storeType;
        this.Country = country;
        this.Location = location;
        this.Address = address;
        this.OpenTime = openTime;
        this.CloseTime = closeTime;
        this.Products = products;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreType() {
        return StoreType;
    }

    public void setStoreType(String storeType) {
        StoreType = storeType;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String closeTime) {
        CloseTime = closeTime;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<Product> products) {
        Products = products;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean containProduct(String productId){
        return Products.stream().filter(o -> o.getProductId().equals(productId)).findFirst().isPresent();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean addProducts(Product newProduct) {
        if (containProduct(newProduct.getProductId())) {
            Products.add(newProduct);
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isActive(){
        LocalTime now = ZonedDateTime.now( ZoneId.of("Asia/Singapore")).toLocalTime() ;
        return (!now.isBefore(LocalTime.parse(getOpenTime())) && now.isBefore(LocalTime.parse(getCloseTime())));
    }
}
