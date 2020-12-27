package com.northwind.repositories;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.services.CategoryService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, Integer> {
    List<Category> findAllByName(String name);
    Category findFirstById(int productId);
}
