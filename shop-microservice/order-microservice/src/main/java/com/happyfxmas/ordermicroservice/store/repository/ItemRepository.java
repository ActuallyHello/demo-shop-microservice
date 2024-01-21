package com.happyfxmas.ordermicroservice.store.repository;

import com.happyfxmas.ordermicroservice.store.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends CrudRepository<Item, UUID> {
    List<Item> saveAll(List<Item> items);
    List<Item> findAllByOrderId(UUID orderId);
}
