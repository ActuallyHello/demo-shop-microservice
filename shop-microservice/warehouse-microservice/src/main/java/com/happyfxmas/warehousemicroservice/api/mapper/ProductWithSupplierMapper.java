package com.happyfxmas.warehousemicroservice.api.mapper;

import com.happyfxmas.warehousemicroservice.api.dto.response.ProductWithSupplierDTO;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import lombok.NonNull;

public class ProductWithSupplierMapper {
    public static ProductWithSupplierDTO makeDTO(@NonNull Product product, @NonNull Supplier supplier) {
        return ProductWithSupplierDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .type(product.getType())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .supplierId(product.getSupplier().getId())
                .supplierDTO(SupplierMapper.makeDTO(supplier))
                .build();
    }
}
