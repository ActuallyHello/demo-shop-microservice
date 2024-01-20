package com.happyfxmas.ordermicroservice.store.repository;

import com.happyfxmas.ordermicroservice.store.model.OrderStatus;

import java.util.Optional;

public interface OrderStatusRepository extends CrudRepository<OrderStatus, Integer> {
    Optional<OrderStatus> findByCode(String code);
}
