package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.handlers.ProductRequest;
import com.northwind.repositories.CategoryRepository;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.ProductService;
import com.northwind.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public Category add(@RequestBody Category category) {
        return categoryRepository.save(new Category(
                sequenceGeneratorService.generateSequence(Category.SEQUENCE_NAME),
                category.name,
                category.description,
                category.picture
        ));
    }
    @PostMapping("/addCategory")
    public ModelAndView addCategory(@ModelAttribute Category category, Model model) {
        if(categoryRepository.findAllByName(category.getName()).isEmpty()) {
            categoryRepository.save(new Category(
                    sequenceGeneratorService.generateSequence(Category.SEQUENCE_NAME),
                    category.name,
                    category.description,
                    category.picture
            ));
        }
        return new ModelAndView("redirect:" + "adminpanel");
    }
}
