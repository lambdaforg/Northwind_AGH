package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.ProductService;
import org.bson.types.Symbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/findProducts")
    public String greetingSubmit(@ModelAttribute ProductRequest request, Model model) {
        model.addAttribute("request", request);
        List<Product> list;
        System.out.println(request.getName());
        System.out.println(request.getPriceFrom());
        System.out.println(request.getPriceTo());
        if(!request.getName().isEmpty()) {
           list = productService.getProductsByName(request.getName());
        }else{
            list = productService.getProducts();
        }
        if(!request.getPriceFrom().isEmpty() && !request.getPriceTo().isEmpty()) {
            System.out.println(Double.parseDouble(request.getPriceFrom()));
            list = productService.getProductByPrice(Double.parseDouble(request.getPriceFrom()), Double.parseDouble(request.getPriceTo()));
        }
        System.out.println(list);
        model.addAttribute("products", list);
        model.addAttribute("findProducts", new ProductRequest());
        return "base";
    }

    @GetMapping("/addProduct")
    public String productForm(Model model) {
        model.addAttribute("product", new ProductRequest());
        return "admin/addproduct";
    }
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute ProductRequest product, Model model) {
        productService.addProduct(product.toProduct());
        List<Product> list = new ProductService(productRepository).getProducts();
        model.addAttribute("products", list);
        model.addAttribute("findProducts", new ProductRequest());
        return "base";
    }
}
