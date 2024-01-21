package com.happyfxmas.ordermicroservice.service;

import com.happyfxmas.ordermicroservice.store.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    Item getById(UUID id);
    List<Item> getAllByOrderId(UUID orderId);
    Item create(Item item);
    List<Item> createAll(List<Item> item);
    void update(Item newItem);
    void delete(Item item);
}
