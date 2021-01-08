package com.northwind.entities;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
public class Product {
    @Transient
    public static final String SEQUENCE_NAME = "product_sequence";
    @Id
    @BsonProperty("id")
    public int id;

    public String name;
    public String quantityPerUnit;
    public double unitPrice;
    public int unitsInStock;
    public int unitsOnOrder;
    public int reorderLevel;
    public boolean discontinued;

    public int supplierID;
    public int categoryId;

    public Product() {
    }
    public Product(String name, String quantityPerUnit, double unitPrice, int unitsInStock, int unitsOnOrder, int reorderLevel, boolean discontinued){
        this.name = name;
        this.quantityPerUnit = quantityPerUnit;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.unitsOnOrder = unitsOnOrder;
        this.reorderLevel = reorderLevel;
        this.discontinued = discontinued;

    }
    public Product(String name, String quantityPerUnit, double unitPrice, int unitsInStock, int unitsOnOrder, int reorderLevel, boolean discontinued, int supplier){
        this.name = name;
        this.quantityPerUnit = quantityPerUnit;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.unitsOnOrder = unitsOnOrder;
        this.reorderLevel = reorderLevel;
        this.discontinued = discontinued;
        this.supplierID = supplier;
    }
    public Product(String name, String quantityPerUnit, double unitPrice, int unitsInStock, int unitsOnOrder, int reorderLevel, boolean discontinued, int categoryId, int supplier) {
        this.name = name;
        this.quantityPerUnit = quantityPerUnit;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.unitsOnOrder = unitsOnOrder;
        this.reorderLevel = reorderLevel;
        this.discontinued = discontinued;
        this.supplierID = supplier;
        this.categoryId = categoryId;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public int getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(int unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public int getCategory() {
        return categoryId;
    }

    public void setCategory(int categoryID) {
        this.categoryId = categoryID;
    }
}
