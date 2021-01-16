package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Order;
import com.northwind.entities.Product;
import com.northwind.handlers.BasketProduct;
import com.northwind.handlers.OrderRequest;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.CategoryRepository;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.CategoryService;
import com.northwind.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@SessionAttributes({"basketProductList", "order"})
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String getHome(Model model) {
        List<Product> list = new ProductService(productRepository).getProductsOffer();
        List<Category> categoryList = categoryRepository.findAll();
        categoryList.add(0, new Category("Wszystkie kategorie", "brk", "brk"));
        model.addAttribute("products", list);
        ProductRequest pr = new ProductRequest();
        model.addAttribute("findProducts", pr);
        model.addAttribute("categoryList", categoryList);
        // httpServletRequest.isUserInRole("ADMIN")
        return "base";
    }
}


