package com.northwind;

import com.northwind.entities.Category;
import com.northwind.entities.Customer;
import com.northwind.repositories.CategoryRepository;
import com.northwind.repositories.CustomerRepository;
import com.northwind.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NorthwindApplication implements CommandLineRunner {

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private CategoryRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(NorthwindApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
	/*Dla testu czy działa baza*/
		repository.deleteAll();

		// save a couple of categories
		repository.save(new Category(
				sequenceGeneratorService.generateSequence(Category.SEQUENCE_NAME),
				"name1",
				"description1",
				"picture1"
		));
		repository.save(new Category(
				sequenceGeneratorService.generateSequence(Category.SEQUENCE_NAME),
				"name2",
				"description2",
				"picture2"
		));

		// fetch all customers
		System.out.println("Categories found with findAll():");
		System.out.println("-------------------------------");
		for (Category category : repository.findAll()) {
			System.out.println(category.name + " " + category.description + " " + category.picture);
		}
		System.out.println();

		// fetch an individual customer
//		System.out.println("Customer found with findByFirstName('Alice'):");
//		System.out.println("--------------------------------");
//		System.out.println(repository.findByFirstName("Alice"));
//
//		System.out.println("Customers found with findByLastName('Smith'):");
//		System.out.println("--------------------------------");
//		for (Customer customer : repository.findByLastName("Smith")) {
//			System.out.println(customer);
//		}

	}
}
