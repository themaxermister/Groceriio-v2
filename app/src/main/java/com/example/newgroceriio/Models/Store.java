package com.example.newgroceriio.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Store {

    private String StoreId;
    private double Latitude;
    private double Longitude;
    private String StoreName;
    private String StoreType;
    private String Location;
    private String Address;
    private String OpeningTime;
    private String ClosingTime;

    private List<Product> StoreProducts;

    public Store() {}

    public Store(String storeId, double latitude, double longitude, String storeName, String storeType, String location, String address, String openingTime, String closingTime, List<Product> storeProducts) {
        StoreId = storeId;
        Latitude = latitude;
        Longitude = longitude;
        StoreName = storeName;
        StoreType = storeType;
        Location = location;
        Address = address;
        OpeningTime = openingTime;
        ClosingTime = closingTime;
        StoreProducts = storeProducts;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
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

    public String getOpeningTime() {
        return OpeningTime;
    }

    public void setOpeningTime(String openingTime) {
        OpeningTime = openingTime;
    }

    public String getClosingTime() {
        return ClosingTime;
    }

    public void setClosingTime(String closingTime) {
        ClosingTime = closingTime;
    }

    public List<Product> getStoreProducts() {
        return StoreProducts;
    }

    public void setStoreProducts(List<Product> storeProducts) {
        StoreProducts = storeProducts;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean containProduct(String productId){
        return StoreProducts.stream().filter(o -> o.getProductId().equals(productId)).findFirst().isPresent();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean addProducts(Product newProduct) {
        if (containProduct(newProduct.getProductId())) {
            StoreProducts.add(newProduct);
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isActive(){
        LocalTime now = ZonedDateTime.now( ZoneId.of("Asia/Singapore")).toLocalTime() ;
        return (!now.isBefore(LocalTime.parse(getOpeningTime())) && now.isBefore(LocalTime.parse(getClosingTime())));
    }
}