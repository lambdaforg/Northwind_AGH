package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.entities.Supplier;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.CategoryService;
import com.northwind.services.ProductService;
import com.northwind.services.SupplierService;
import org.bson.types.Symbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("suppliers", supplierService.getSuppliers());
        return "/admin/allproducts";
    }
    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable Integer id, Model model){
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("selectCategories", categoryService.getCategories());
        model.addAttribute("selectSuppliers", supplierService.getSuppliers());
        return "/admin/editProduct";
    }
    @PostMapping("/deleteProduct/{id}")
    public ModelAndView deleteProduct(@PathVariable Integer id, Model model){
        productService.deleteProduct(id);
        return new ModelAndView("redirect:" + "/allProducts");
    }
    @PostMapping("/saveProduct")
    public ModelAndView saveProduct(@ModelAttribute ProductRequest product, Model model) {
        productService.updateProduct(product.toProduct(), product.getCategory());
        return new ModelAndView("redirect:" + "/allProducts");
    }
    @GetMapping("/editSupplier/{id}")
    public String editSupplier(@PathVariable Integer id, Model model){
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "/admin/editSupplier";
    }
    @PostMapping("/deleteSupplier/{id}")
    public ModelAndView deleteSupplier(@PathVariable Integer id, Model model){
        supplierService.deleteSupplier(id);
        return new ModelAndView("redirect:" + "/allProducts");
    }
    @PostMapping("/saveSupplier")
    public ModelAndView saveSupplier(@ModelAttribute Supplier supplier, Model model) {
        supplierService.updateSupplier(supplier);
        return new ModelAndView("redirect:" + "/allProducts");
    }
    @GetMapping("/editCategory/{id}")
    public String editCategory(@PathVariable Integer id, Model model){
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "/admin/editCategory";
    }
    @PostMapping("/deleteCategory/{id}")
    public ModelAndView deleteCategory(@PathVariable Integer id, Model model){
        categoryService.deleteCategory(id);
        return new ModelAndView("redirect:" + "/allProducts");
    }
    @PostMapping("/saveCategory")
    public ModelAndView saveCategory(@ModelAttribute Category category, Model model) {
        categoryService.saveCategory(category);
        return new ModelAndView("redirect:" + "/allProducts");
    }
}
