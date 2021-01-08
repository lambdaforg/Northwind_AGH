package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.services.CategoryService;
import com.northwind.services.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private IconService iconService;

    @PostMapping
    public Category add(@RequestBody Category cate) {
        var category = new Category(
                cate.name,
                cate.description,
                cate.picture
        );
        categoryService.saveCategory(category);
        return category;
    }
    @PostMapping("/dashboard/addCategory")
    public ModelAndView addCategory(@ModelAttribute Category category, Model model) {
        if(categoryService.getCategoriesByName(category.getName()).isEmpty()) {
            categoryService.addCategory(new Category(
                    category.name,
                    category.description,
                    category.picture
            ));
        }
        return new ModelAndView("redirect:" + "adminpanel");
    }
    @GetMapping("/dashboard/editCategory/{id}")
    public String editCategory(@PathVariable int id, Model model){
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("selectIcons", iconService.getIcons());
        return "/admin/editCategory";
    }
    @PostMapping("/dashboard/deleteCategory/{id}")
    public ModelAndView deleteCategory(@PathVariable int id, Model model){
        categoryService.deleteCategory(id);
        return new ModelAndView("redirect:" + "/dashboard/managementAll");
    }
    @PostMapping("/dashboard/saveCategory")
    public ModelAndView saveCategory(@ModelAttribute Category category, Model model) {
        categoryService.saveCategory(category);
        return new ModelAndView("redirect:" + "/dashboard/managementAll");
    }
}
