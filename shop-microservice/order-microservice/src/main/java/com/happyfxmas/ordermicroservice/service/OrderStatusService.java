package com.happyfxmas.ordermicroservice.service;

import com.happyfxmas.ordermicroservice.store.model.OrderStatus;

import java.util.List;

public interface OrderStatusService {
    OrderStatus getById(Integer id);
    List<OrderStatus> getAll();
    void create(String code);
    void update(Integer id, OrderStatus orderStatus);
    void delete(OrderStatus orderStatus);
}
