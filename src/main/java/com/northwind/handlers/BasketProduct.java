package com.northwind.handlers;

public class BasketProduct {
    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    private int ProductId;
    private int Count;
    public BasketProduct(){

    }
    public BasketProduct(int productId, int count, String productName, double pricePerUnit) {
        ProductId = productId;
        Count = count;
        ProductName = productName;
        PricePerUnit = pricePerUnit;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    private String ProductName;
    private double PricePerUnit;

    public double getPricePerUnit() {
        return PricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        PricePerUnit = pricePerUnit;
    }
    public double getTotalPrice()
    {
        return PricePerUnit * Count;
    }
}
