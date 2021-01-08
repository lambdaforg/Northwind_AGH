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
    @Autowired
    private CategoryService categoryService;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String productId) {
        return productRepository.findFirstById(productId);
    }

    public List<Product> getProductsByName(String productName) {
        return productRepository.findAllByNameContains(productName);
    }

    public List<Product> getProductByPrice(double priceFrom, double priceTo) {
        return productRepository.findAllByUnitPriceBetweenOrderByUnitPrice(priceFrom - 0.001d, priceTo + 0.001d);
    }

    public Product addProduct(Product product, String categoryId) {
        return productRepository.save(new Product(
                product.name,
                product.quantityPerUnit,
                product.unitPrice,
                product.unitsInStock,
                product.unitsOnOrder,
                product.reorderLevel,
                product.discontinued,
                categoryId,
                product.supplierID
        ));
    }

    public void updateProduct(Product product, String categoryId) {
        product.categoryId = categoryId;
        productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
