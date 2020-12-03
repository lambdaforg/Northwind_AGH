package com.northwind.repositories;

import com.northwind.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Integer> {
    Product findFirstById(int productId);
    List<Product> findAllByNameContains(String name);
}
