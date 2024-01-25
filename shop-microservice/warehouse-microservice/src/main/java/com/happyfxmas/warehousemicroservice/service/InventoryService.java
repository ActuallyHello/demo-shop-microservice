package com.happyfxmas.warehousemicroservice.service;

import com.happyfxmas.warehousemicroservice.api.dto.request.InventoryRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.InventoryUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.store.model.Inventory;
import com.happyfxmas.warehousemicroservice.store.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface InventoryService {
    Inventory getById(UUID id);
    Inventory getByProduct(Product product);
    Inventory create(Inventory inventory);
    Inventory update(Inventory newinventory);
    void delete(Inventory inventory);
}
