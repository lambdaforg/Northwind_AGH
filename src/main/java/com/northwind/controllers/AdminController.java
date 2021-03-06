package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Icon;
import com.northwind.entities.Supplier;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.CategoryRepository;
import com.northwind.repositories.SupplierRepository;
import com.northwind.services.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private IconService iconService;

    @GetMapping("/dashboard/adminpanel")
    public String productForm(Model model) {
        model.addAttribute("product", new ProductRequest());
        model.addAttribute("category", new Category());
        model.addAttribute("supplier", new Supplier());
        List<Category> categories = categoryRepository.findAll();
        List<Supplier> suppliers = supplierRepository.findAll();
        List<Icon> icons = iconService.getIcons();
        model.addAttribute("selectCategories", categories);
        model.addAttribute("selectSuppliers", suppliers);
        model.addAttribute("selectIcons", icons);

        return "admin/addproduct";
    }
}
