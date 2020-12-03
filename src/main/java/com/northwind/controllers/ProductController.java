package com.northwind.controllers;

import com.northwind.entities.Product;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/findProducts")
    public String greetingSubmit(@ModelAttribute ProductRequest request, Model model) {
        model.addAttribute("request", request);
        List<Product> list = productService.getProductsByName(request.getName());
        model.addAttribute("products", list);
        model.addAttribute("findProducts", new ProductRequest());
        return "base";
    }


}
