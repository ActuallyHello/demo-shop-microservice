package com.happyfxmas.ordermicroservice.service;

import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order getById(UUID id);
    List<Order> getAllByCustomerId(UUID customerId);
    List<Order> getAllByStatusIn(List<OrderStatus> orderStatuses);
    Order create(Order order);
    void update(Order newOrder);
    void delete(Order order);
}
