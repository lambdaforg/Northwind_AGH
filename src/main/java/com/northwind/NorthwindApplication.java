package com.northwind;

import com.northwind.entities.Category;
import com.northwind.entities.Product;
import com.northwind.repositories.CategoryRepository;
import com.northwind.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;

@SpringBootApplication
public class NorthwindApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository repository;
	@Autowired
	private ProductRepository productRepository;
	public static void main(String[] args) {
		SpringApplication.run(NorthwindApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		// Format prices
		DecimalFormat df = new DecimalFormat("####.##");
		productRepository.findAll()
				.forEach(product -> {
					product.setUnitPrice(Double.parseDouble(df.format(product.getUnitPrice())));
					productRepository.save(product);
				});

	/*Dla testu czy działa baza*/
//		repository.deleteAll();
//		productRepository.deleteAll();
		// save a couple of categories
//		repository.save(new Category(
//				"name1",
//				"description1",
//				"picture1"
//		));
//
//		repository.save(new Category(
//				"name2",
//				"description2",
//				"picture2"
//		));
//
//
//		productRepository.save(new Product(
//				"test",
//				"test2",
//				2.0d,
//				3,
//				4,
//				5,
//				false
//		));

//		// fetch all customers
//		System.out.println("Categories found with findAll():");
//		System.out.println("-------------------------------");
//		for (Category category : repository.findAll()) {
//			System.out.println(category.name + " " + category.description + " " + category.picture);
//		}
//		for (Product product : productRepository.findAll()) {
//			System.out.println(product.name);
//		}
//		System.out.println();
	}
}
