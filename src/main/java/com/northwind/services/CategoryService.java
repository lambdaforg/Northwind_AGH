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

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ProductCategory getCategoryProduct(String categoryId){
        var inte = Integer.getInteger(categoryId);
        var category = categoryRepository.findFirstById(Integer.parseInt(categoryId));
        ProductCategory productCategory = new ProductCategory();
        productCategory.categoryId = category.getId();
        productCategory.categoryName = category.getName();
        return productCategory;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findFirstById(id);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getCategoriesByName(String categoryName){
        return categoryRepository.findAllByName(categoryName);
    }
}
