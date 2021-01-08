package com.northwind.services;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.entities.ProductCategory;
import com.northwind.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

//    public ProductCategory getCategoryProduct(String categoryId){
//        var inte = Integer.getInteger(categoryId);
//        var category = categoryRepository.findFirstById(categoryId);
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.categoryId = category.getId();
//        productCategory.categoryName = category.getName();
//        return productCategory;
//    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findFirstById(id);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getCategoriesByName(String categoryName){
        return categoryRepository.findAllByName(categoryName);
    }

    public void addCategory(Category category) {
        category.setId(sequenceGeneratorService.generateSequence(Category.SEQUENCE_NAME));
        categoryRepository.save(category);
    }
}
