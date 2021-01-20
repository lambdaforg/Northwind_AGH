package com.northwind.handlers;

import com.northwind.entities.Category;
import com.northwind.entities.OrderDetail;
import com.northwind.entities.Product;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;

import java.util.List;

public class ProductCategoryHandler extends Product {
    private List<OrderDetail> result;
    private Category category;

    /*Query dla kategorii*/
    private List<String> item;
    private int arrSum;

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }

    public int getArrSum() {
        return this.arrSum;
    }

    public void setArrSum(int arrSum) {
        this.arrSum = arrSum;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<OrderDetail> getResult() {
        return result;
    }

    public void setResult(List<OrderDetail> result) {
        this.result = result;
    }

    /*// private List<Product> orderDetails;
    private OrderDetail orderDetails;
    private long total;
    private List<Nested> report;
    public int productId;
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    class Nested{

        public OrderDetail orderDetails;
        public long total;

        public OrderDetail getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(OrderDetail orderDetails) {
            this.orderDetails = orderDetails;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }
    }

    public List<Nested> getReport() {
        return report;
    }


    public void setReport(List<Nested> report) {
        this.report = report;
    }

    public OrderDetail getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetail orderDetail) {
        this.orderDetails = orderDetail;
    }

   *//* public List<Product> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<Product> orderDetails) {
        this.orderDetails = orderDetails;
    }*//*

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    *//* private Product product;
    private Category category;

    public ProductCategoryHandler(Product product, Category category) {
        this.product = product;
        this.category = category;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }*/
}
