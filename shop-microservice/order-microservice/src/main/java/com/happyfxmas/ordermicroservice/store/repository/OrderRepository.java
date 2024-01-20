package com.happyfxmas.ordermicroservice.store.repository;

import com.happyfxmas.ordermicroservice.store.model.Order;
import com.happyfxmas.ordermicroservice.store.model.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
    List<Order> findAllByCustomerId(UUID customerId);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
