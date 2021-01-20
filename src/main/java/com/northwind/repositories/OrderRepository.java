package com.northwind.repositories;

import com.northwind.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Integer> {
    Order findFirstById(int id);

    List<Order> getAllByOrderDateIsStartingWith(Date orderDate);
}
