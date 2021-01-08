package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.handlers.FileUploadUtil;
import com.northwind.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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

    @PostMapping("/addCategory")
    public ModelAndView addCategory(@ModelAttribute Category category, @RequestParam("image") MultipartFile multipartFile, Model model) {
        if (categoryService.getCategoriesByName(category.getName()).isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            Category cat = new Category();
            cat.setName(category.name);
            cat.setDescription(category.description);
            cat.setPicture(fileName);
            categoryService.saveCategory(cat);
            String uploadDir = "user-photos/" + cat.getId();
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("redirect:" + "adminpanel");
    }

    @GetMapping("/editCategory/{id}")
    public String editCategory(@PathVariable String id, Model model) {
        Category category = categoryService.getCategoryById(id);
        System.out.println(category.getId());
        model.addAttribute("category", category);
        return "/admin/editCategory";
    }

    @PostMapping("/deleteCategory/{id}")
    public ModelAndView deleteCategory(@PathVariable String id, Model model) {
        categoryService.deleteCategory(id);
        return new ModelAndView("redirect:" + "/managementAll");
    }

    @PostMapping("/saveCategory")
    public ModelAndView saveCategory(@ModelAttribute Category category, @RequestParam("image") MultipartFile multipartFile, Model model) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (!fileName.isEmpty()) {
            category.setPicture(fileName);
            String uploadDir = "user-photos/" + category.getId();
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        categoryService.saveCategory(category);
        return new ModelAndView("redirect:" + "/managementAll");
    }
}
