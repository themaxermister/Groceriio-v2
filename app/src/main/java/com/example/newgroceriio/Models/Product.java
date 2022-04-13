package com.example.newgroceriio.Models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;

public class Product {

    private String ProductId;
    private String ProductName;
    private String ProductBrand;
    private String ProductType;
    private String SecondaryProductType;
    private String Description;
    private String Ingredients;
    private String Metric;
    private String Country;
    private String ImgUrl;
    private double Price;

    public Product(){

    }

    public Product(String productId, String productName, String productBrand, String productType, String secondaryProductType, String description, String ingredients, String metric, String country, Integer quantitySold, Integer quantityAvailable, Double price, String imgUrl){
        ProductId = productId;
        ProductName = productName;
        ProductBrand = productBrand;
        ProductType = productType;
        SecondaryProductType = secondaryProductType;
        Description = description;
        Ingredients = ingredients;
        Metric = metric;
        Country = country;
        ImgUrl = imgUrl;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductBrand() {
        return ProductBrand;
    }

    public void setProductBrand(String productBrand) {
        ProductBrand = productBrand;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getSecondaryProductType() {
        return SecondaryProductType;
    }

    public void setSecondaryProductType(String secondaryProductType) {
        SecondaryProductType = secondaryProductType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getMetric() {
        return Metric;
    }

    public void setMetric(String metric) {
        Metric = metric;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public double getPrice() {
        BigDecimal p = new BigDecimal(this.Price);
        p = p.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return p.doubleValue();
    }


    public void setPrice(double price) {
        Price = price;
    }
}
