package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Order;
import com.northwind.entities.Product;
import com.northwind.services.CategoryService;
import com.northwind.services.OrderService;
import com.northwind.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReportsController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/dashboard/reports")
    public String getReports(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("topCategory", getTopCategory());
        model.addAttribute("topProduct", getTopProduct());
        return "reports/menu";
    }

    /**
     * @return the most popular product
     */
    public Product getTopProduct() {
        List<Order> orders = orderService.getAllOrders();
        Map<Integer, Integer> orderedProductsCount = new HashMap<>();
        orders.forEach(order ->
                order.getOrderDetails()
                        .forEach(orderDetail ->
                                orderedProductsCount.put(orderDetail.getProductID(),
                                        orderedProductsCount.getOrDefault(orderDetail.getProductID(), 0)
                                                + (int) orderDetail.getQuantity())
                        )
        );
        if (orderedProductsCount.size() > 0) {
            return productService.getProduct(orderedProductsCount
                    .entrySet()
                    .stream()
                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                    .get()
                    .getKey());
        } else {
            return null;
        }
    }

    /**
     * @return the most popular category
     */
    public Category getTopCategory() {
        //TODO method logic
        return categoryService.getCategories().stream().findFirst().orElse(null);
    }
}

