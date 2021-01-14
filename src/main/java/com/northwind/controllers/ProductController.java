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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;

    @PostMapping("/findProducts")
    public String greetingSubmit(@ModelAttribute ProductRequest request, Model model) {
        model.addAttribute("request", request);
        List<Product> list;
        System.out.println(request.getName());
        System.out.println(request.getPriceFrom());
        System.out.println(request.getPriceTo());
        if (!request.getName().isEmpty()) {
            list = productService.getProductsByName(request.getName());
        } else {
            list = productService.getProducts();
        }
        if (!request.getPriceFrom().isEmpty() && !request.getPriceTo().isEmpty()) {
            System.out.println(Double.parseDouble(request.getPriceFrom()));
            list = productService.getProductByPrice(Double.parseDouble(request.getPriceFrom()), Double.parseDouble(request.getPriceTo()));
        }
        System.out.println(list);
        model.addAttribute("products", list);
        model.addAttribute("findProducts", new ProductRequest());
        return "base";
    }

    @GetMapping("/dashboard/addProduct")
    public RedirectView productForm(Model model) {
        return new RedirectView("/dashboard/adminpanel");
    }

    @PostMapping("/dashboard/addProduct")
    public ModelAndView addProduct(@ModelAttribute ProductRequest product, Model model) {
        productService.addProduct(product.toProduct(), product.getCategory());
        return new ModelAndView("redirect:" + "/");
    }

    @GetMapping("/dashboard/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("selectCategories", categoryService.getCategories());
        model.addAttribute("selectSuppliers", supplierService.getSuppliers());
        return "/admin/editProduct";
    }

    @PostMapping("/dashboard/deleteProduct/{id}")
    public ModelAndView deleteProduct(@PathVariable int id, Model model) {
        productService.deleteProduct(id);
        return new ModelAndView("redirect:" + "/dashboard/managementAll");
    }

    @PostMapping("/dashboard/saveProduct")
    public ModelAndView saveProduct(@ModelAttribute Product product, Model model) {

        productService.updateProduct(product, product.categoryId);
        return new ModelAndView("redirect:" + "/dashboard/managementAll");
    }
}
