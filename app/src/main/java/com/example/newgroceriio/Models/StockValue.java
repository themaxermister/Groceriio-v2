package com.example.newgroceriio.Models;

import java.util.ArrayList;

public class StockValue {

    private String ProductStockId;
    private int QuantityAvailable;
    private int QuantitySold;
    private String StoreStockId;

    public String getProductStockId() {
        return ProductStockId;
    }

    public void setProductStockId(String productStockId) {
        ProductStockId = productStockId;
    }

    public int getQuantityAvailable() {
        return QuantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        QuantityAvailable = quantityAvailable;
    }

    public int getQuantitySold() {
        return QuantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        QuantitySold = quantitySold;
    }

    public String getStoreStockId() {
        return StoreStockId;
    }

    public void setStoreStockId(String storeStockId) {
        StoreStockId = storeStockId;
    }

    public void reduceStockByOne(){
        this.QuantityAvailable -= 1;
    }
}