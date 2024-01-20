package com.happyfxmas.ordermicroservice.service;

import com.happyfxmas.ordermicroservice.api.dto.request.OrderRequestDTO;
import com.happyfxmas.ordermicroservice.store.model.Order;
import com.happyfxmas.ordermicroservice.store.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order getById(UUID id);
    List<Order> getAll();
    Order create(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus);
    void update(Order oldOrder, Order newOrder);
    void delete(Order order);
}
