package com.northwind.services;

import com.northwind.entities.Product;
import com.northwind.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    public Product getProduct(int productId){
        return productRepository.findFirstById(productId);
    }
    public List<Product> getProductsByName(String productName){
        return productRepository.findAllByNameContains(productName);
    }
}
