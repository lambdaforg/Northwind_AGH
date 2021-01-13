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
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(int productId) {
        return productRepository.findFirstById(productId);
    }

    public List<Product> getProductsByName(String productName) {
        return productRepository.findAllByNameContains(productName);
    }

    public List<Product> getProductByPrice(double priceFrom, double priceTo) {
        return productRepository.findAllByUnitPriceBetweenOrderByUnitPrice(priceFrom - 0.001d, priceTo + 0.001d);
    }

    public Product addProduct(Product product, int categoryId) {
        return productRepository.save(new Product(
                sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME),
                product.name,
                product.quantityPerUnit,
                product.unitPrice,
                product.unitsInStock,
                product.unitsOnOrder,
                product.reorderLevel,
                product.discontinued,
                categoryId,
                product.supplierId
        ));
    }

    public void updateProduct(Product product, int categoryId) {
        product.categoryId = categoryId;
        productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
