package com.happyfxmas.ordermicroservice.service;

import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order getById(UUID id);
    List<Order> getAllByCustomerId(UUID customerId);
    List<Order> getAllByStatus(OrderStatus orderStatus);
    Order create(Order order, OrderStatus orderStatus);
    void update(Order oldOrder, Order newOrder);
    void delete(Order order);
}
