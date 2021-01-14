package com.northwind.controllers;

import com.northwind.entities.Order;
import com.northwind.entities.Product;
import com.northwind.handlers.BasketProduct;
import com.northwind.handlers.OrderRequest;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.ProductRepository;
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

    @GetMapping("/")
    public String getHome(Model model) {
        List<Product> list = new ProductService(productRepository).getProductsOffer();
        model.addAttribute("products", list);
        model.addAttribute("findProducts", new ProductRequest());
        // httpServletRequest.isUserInRole("ADMIN")
        return "base";
    }
}


