package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.ProductService;
import com.northwind.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String getHome(Model model){
        List<Product> list = new ProductService(productRepository).getProducts();
        model.addAttribute("products", list);
        model.addAttribute("findProducts", new ProductRequest());
        return "base";
    }

    /** TYMCZASOWO WSZYSTKIE ROUTE W TYM KONTROLERZE,
     * jeśli robisz swoja cześć widoku to przenieś do odpowiedniego kontrolera
     */
    @GetMapping("/signup")
    public String getRegistrationView(){
        return "account/signup";
    }
    @GetMapping("/signin")
    public String getLoginView(){
        return "account/signin";
    }

     /*   @GetMapping("/home")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }*/

}


