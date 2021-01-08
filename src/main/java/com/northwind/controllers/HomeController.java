package com.northwind.controllers;

import com.northwind.entities.Product;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.ProductService;
import org.apache.catalina.realm.GenericPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String getHome(Model model, HttpServletRequest httpServletRequest){
        List<Product> list = new ProductService(productRepository).getProducts();
        model.addAttribute("products", list);
        model.addAttribute("findProducts", new ProductRequest());
       // httpServletRequest.isUserInRole("ADMIN")
        return "base";
    }
}


