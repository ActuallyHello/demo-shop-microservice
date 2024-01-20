package com.happyfxmas.ordermicroservice.store.repository;

import com.happyfxmas.ordermicroservice.store.model.ItemStatus;

import java.util.Optional;

public interface ItemStatusRepository extends CrudRepository<ItemStatus, Integer> {
    Optional<ItemStatus> findByCode(String code);
}
