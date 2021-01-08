package com.northwind;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.repositories.CategoryRepository;
import com.northwind.repositories.ProductRepository;
import com.northwind.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@SpringBootApplication
public class NorthwindApplication implements CommandLineRunner {

	@Autowired
	private ProductService productService;
	public static void main(String[] args) {
		SpringApplication.run(NorthwindApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		productService.getProducts()
				.forEach(product -> {
					product.setUnitPrice(BigDecimal.valueOf(product.getUnitPrice())
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue());
					productService.updateProduct(product, product.categoryId);
				});
	}
}
