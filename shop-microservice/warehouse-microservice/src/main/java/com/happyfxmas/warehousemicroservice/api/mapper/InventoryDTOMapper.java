package com.happyfxmas.warehousemicroservice.api.mapper;

import com.happyfxmas.warehousemicroservice.api.dto.InventoryDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.InventoryRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.InventoryUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.store.model.Inventory;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import lombok.NonNull;

public class InventoryDTOMapper {
    public static Inventory makeModel(@NonNull InventoryRequestDTO inventoryRequestDTO, Product product) {
        return Inventory.builder()
                .quantity(inventoryRequestDTO.getQuantity())
                .product(product)
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

    public static Inventory makeModel(InventoryUpdateRequestDTO inventoryUpdateRequestDTO, Inventory oldInventory) {
        if (inventoryUpdateRequestDTO.getQuantity() == null) {
            throw new IllegalArgumentException("There are no fields to update in inventory!");
        }
        return Inventory.builder()
                .id(oldInventory.getId())
                .product(oldInventory.getProduct())
                .updatedAt(oldInventory.getUpdatedAt())
                .quantity(inventoryUpdateRequestDTO.getQuantity())
                .build();
    }
}
