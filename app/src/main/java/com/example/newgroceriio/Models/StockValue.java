package com.example.newgroceriio.Models;

public class StockValue {

    private Product ProductObject;
    private Store OutletObject;
    private Integer QuantitySold;
    private Integer QuantityAvailable;
    private boolean HavePromo;

    public StockValue(){}

    public StockValue(Product productObject, Store outletObject, Integer quantitySold, Integer quantityAvailable, boolean havePromo){
        ProductObject = productObject;
        OutletObject = outletObject;
        QuantitySold = quantitySold;
        QuantityAvailable = quantityAvailable;
        HavePromo = havePromo;
    }

    public Product getProductObject() {
        return ProductObject;
    }

    public void setProductObject(Product productObject) {
        ProductObject = productObject;
    }

    public Store getOutletObject() {
        return OutletObject;
    }

    public void setOutletObject(Store outletObject) {
        OutletObject = outletObject;
    }

    public Integer getQuantitySold() {
        return QuantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        QuantitySold = quantitySold;
    }

    public Integer getQuantityAvailable() {
        return QuantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        QuantityAvailable = quantityAvailable;
    }

    public boolean isHavePromo() {
        return HavePromo;
    }

    public void setHavePromo(boolean havePromo) {
        HavePromo = havePromo;
    }
}