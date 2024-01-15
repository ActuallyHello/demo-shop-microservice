package com.happyfxmas.warehousemicroservice.api.mapper;

import com.happyfxmas.warehousemicroservice.api.dto.InventoryDTO;
import com.happyfxmas.warehousemicroservice.api.dto.response.ProductWithInventoryDTO;
import com.happyfxmas.warehousemicroservice.store.model.Inventory;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import lombok.NonNull;

public class ProductWithInventoryMapper {
    public static ProductWithInventoryDTO makeDTO(@NonNull Product product, Inventory inventory) {
        var inventoryDTO = inventory != null
                ? InventoryMapper.makeDTO(inventory)
                : null;
        return ProductWithInventoryDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .type(product.getType())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .supplierId(product.getSupplier().getId())
                .inventoryDTO(inventoryDTO)
                .build();
    }
}
