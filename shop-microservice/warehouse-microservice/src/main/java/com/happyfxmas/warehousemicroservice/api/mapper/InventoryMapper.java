package com.happyfxmas.warehousemicroservice.api.mapper;

import com.happyfxmas.warehousemicroservice.api.dto.InventoryDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.InventoryRequestDTO;
import com.happyfxmas.warehousemicroservice.store.model.Inventory;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import lombok.NonNull;

public class InventoryMapper {
    public static Inventory makeModel(@NonNull InventoryRequestDTO inventoryRequestDTO) {
        return Inventory.builder()
                .quantity(inventoryRequestDTO.getQuantity())
                .build();
    }

    public static InventoryDTO makeDTO(@NonNull Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .quantity(inventory.getQuantity())
                .updatedAt(inventory.getUpdatedAt())
                .productId(inventory.getProduct().getId())
                .build();
    }
}
