package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.services.CategoryService;
import com.northwind.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportsController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/reports")
    public String getReports(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("topCategory", getTopCategory());
        return "reports/menu";
    }

    /**
     * @return the most popular products
     */
    public Product getTopProduct() {
        //TODO
        return null;
    }

    /**
     * @return the most popular category
     */
    public Category getTopCategory() {
        //TODO
        return categoryService.getCategories().stream().findFirst().orElse(null);
    }
}

