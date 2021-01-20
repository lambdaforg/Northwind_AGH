package com.northwind.handlers;

import com.northwind.entities.OrderDetail;

public class ProductFreq {

    private String productName;
    private int total;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
