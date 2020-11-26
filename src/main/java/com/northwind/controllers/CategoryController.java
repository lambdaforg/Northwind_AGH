package com.northwind.controllers;

import com.northwind.entities.Category;
import com.northwind.repositories.CategoryRepository;
import com.northwind.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
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
}
