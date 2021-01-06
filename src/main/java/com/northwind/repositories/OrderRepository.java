package com.northwind.repositories;

import com.northwind.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Integer> {
    Order findFirstById(int id);
}
