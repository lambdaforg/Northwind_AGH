package com.northwind.services;

import com.northwind.entities.Order;
import com.northwind.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void removeOrder(int id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersRange(Date dateFrom, Date dateTo) {
        return orderRepository.getOrdersRange(dateFrom, dateTo);
    }

    public Order getOrder(int id) {
        return orderRepository.findFirstById(id);
    }

    public List<Order> getOrdersFromPeriod(Date date) {
        return orderRepository.getAllByOrderDateIsStartingWith(date);
    }
}
