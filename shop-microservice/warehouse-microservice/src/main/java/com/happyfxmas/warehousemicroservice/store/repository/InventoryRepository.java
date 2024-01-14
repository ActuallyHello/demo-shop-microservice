package com.happyfxmas.warehousemicroservice.store.repository;

import com.happyfxmas.warehousemicroservice.store.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
}
