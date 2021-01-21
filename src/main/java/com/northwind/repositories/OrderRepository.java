package com.northwind.repositories;

import com.northwind.entities.Order;
import com.northwind.handlers.ProductCategoryHandler;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Integer> {
    Order findFirstById(int id);

    List<Order> getAllByOrderDateIsStartingWith(Date orderDate);

    @Query("{ 'orderDate' : { $gte: ?0, $lte: ?1 } }")
    List<Order> getOrdersRange(Date dateFrom, Date dateTo);
}
