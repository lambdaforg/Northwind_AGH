package com.northwind.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

@Service
public class InitDatabaseService {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    public void initProducts() {
        productService.getProducts()
                .forEach(product -> {
                    product.setUnitPrice(BigDecimal.valueOf(product.getUnitPrice())
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue());
                    productService.updateProduct(product, product.categoryId);
                });
    }
    public void initOrders() {
        orderService.getAllOrders()
                .forEach(order -> {
                    order.setRequireDate(
                            addDays(order.orderDate, 2)
                    );

                    if (randInt(-1, 10) > 0) {
                        order.setShippedDate(addDays(order.orderDate, randInt(1, 5)));
                    }
                    order.getOrderDetails()
                            .forEach(orderDetail ->
                                    orderDetail.setUnitPrice(
                                            productService.getProduct(orderDetail.productID).getUnitPrice()
                                    ));
                    orderService.saveOrder(order);
                });
    }

    private Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    private int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
