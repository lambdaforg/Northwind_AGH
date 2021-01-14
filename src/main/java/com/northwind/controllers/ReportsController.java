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
        setTopCategory(model);
        setTopProduct(model);
        return "reports/menu";
    }

    /**
     * Finds the most popular product and order count
     */
    public void setTopProduct(Model model) {
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
        Product product;
        int productCount;
        if (orderedProductsCount.size() > 0) {
            product = productService.getProduct(orderedProductsCount
                    .entrySet()
                    .stream()
                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                    .get()
                    .getKey());
            productCount = orderedProductsCount.get(product.getId());
        } else {
            product = null;
            productCount = 0;
        }
        model.addAttribute("topProduct", product);
        model.addAttribute("topProductCount", productCount);
    }

    /**
     * Finds the most popular category and order count
     */
    public void setTopCategory(Model model) {
        List<Order> orders = orderService.getAllOrders();
        Map<Integer, Integer> orderedCategoriesCount = new HashMap<>();
        orders.forEach(order ->
                order.getOrderDetails()
                        .forEach(orderDetail -> {
                            int categoryId = productService.getProduct(orderDetail.productID).categoryId;
                            orderedCategoriesCount.put(categoryId,
                                    orderedCategoriesCount.getOrDefault(categoryId, 0)
                                            + (int) orderDetail.getQuantity());
                        })
        );
        Category category;
        int categoryCount;
        if (orderedCategoriesCount.size() > 0) {
            category = categoryService.getCategoryById(orderedCategoriesCount
                    .entrySet()
                    .stream()
                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                    .get()
                    .getKey());
            categoryCount = orderedCategoriesCount.get(category.getId());
        } else {
            category = null;
            categoryCount = 0;
        }
        model.addAttribute("topCategory", category);
        model.addAttribute("topCategoryCount", categoryCount);
    }
}

