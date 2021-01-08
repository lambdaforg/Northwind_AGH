package com.northwind.handlers;

public class DtoProduct {
    public DtoProduct(int id, String name, double unitPrice, int unitsInStock, String category, String supplier) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.category = category;
        this.supplier = supplier;
    }

    public int id;
    public String name;
    public double unitPrice;
    public int unitsInStock;
    public String category;
    public String supplier;
}
