package com.happyfxmas.warehousemicroservice.service;

import com.happyfxmas.warehousemicroservice.api.dto.request.InventoryRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.InventoryUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.store.model.Inventory;
import com.happyfxmas.warehousemicroservice.store.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface InventoryService {
    Optional<Inventory> getById(UUID id);
    Optional<Inventory> getByProduct(Product product);
    Inventory create(InventoryRequestDTO inventoryRequestDTO, Product product);
    Inventory update(InventoryUpdateRequestDTO inventoryUpdateRequestDTO, Inventory inventory);
    void delete(Inventory inventory);
}
