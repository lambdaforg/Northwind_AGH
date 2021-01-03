package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.CategoryService;
import com.northwind.services.ProductService;
import com.northwind.services.SupplierService;
import org.bson.types.Symbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ManagementController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;
    @GetMapping("/allProducts")
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "/admin/allproducts";
    }
    @GetMapping("/allCategories")
    public String allCategories(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "/admin/allcategories";
    }
    @GetMapping("/allSuppliers")
    public String allSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.getSuppliers());
        return "/admin/allsuppliers";
    }
}
