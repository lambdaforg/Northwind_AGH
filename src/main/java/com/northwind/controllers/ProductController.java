package com.northwind.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.handlers.ProductRequest;
import com.northwind.services.CategoryService;
import com.northwind.services.ProductService;
import com.northwind.services.SupplierService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        Query query = new Query();
            if(request.isDescIs()) {
                productService.setSort("DESC");
            query.with(Sort.by(Sort.Direction.DESC, "unitPrice"));
            }
            if(request.isAscIs()) {
                productService.setSort("ASC");
            query.with(Sort.by(Sort.Direction.ASC, "unitPrice"));
            }
        if(!request.getPriceFrom().isEmpty() && !request.getPriceTo().isEmpty()){
            query.addCriteria(Criteria.where("unitPrice").lte( Double.parseDouble(request.getPriceTo())).
                    gte(  Double.parseDouble(request.getPriceFrom())));
        }else if(!request.getPriceFrom().isEmpty()){
            query.addCriteria(Criteria.where("unitPrice").gte(  Double.parseDouble(request.getPriceFrom())));
        }else if(!request.getPriceTo().isEmpty()){
            query.addCriteria(Criteria.where("unitPrice").lte( Double.parseDouble(request.getPriceTo())));
        }
        if(!request.getName().isEmpty()){
            System.out.println(request.getName());
            query.addCriteria(Criteria.where("name").regex(request.getName()));
          // query.addCriteria(Criteria.where("name").)
        }
        if(request.getSelectedCategory() != null) {
            if (!request.getSelectedCategory().name.equals("Wszystkie kategorie")) {
                query.addCriteria(Criteria.where("categoryId").is(request.getSelectedCategory().getId()));
            }
        }

        query.addCriteria(Criteria.where("$where").is("this.unitsInStock > this.unitsOnOrder"));
        List<Product> products = productService.queryProduct(query);
        model.addAttribute("products", products);
        model.addAttribute("findProducts", request);
        //model.addAttribute("findProducts", new ProductRequest(request.isDescIs(), request.isAscIs()));
        List<Category> categoryList = categoryService.getCategories();
        categoryList.add(0, new Category("Wszystkie kategorie", "brk", "brk"));
        model.addAttribute("categoryList", categoryList);
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
