package com.happyfxmas.warehousemicroservice.api.mapper;

import com.happyfxmas.warehousemicroservice.api.dto.ProductDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.ProductRequestDTO;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.model.enums.ProductType;
import lombok.NonNull;

public class ProductMapper {
    public static ProductDTO makeDTO(@NonNull Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .type(product.getType())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .supplierId(product.getSupplier().getId())
                .build();
    }

    public static Product makeModel(@NonNull ProductRequestDTO productRequestDTO) {
        return Product.builder()
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .type(ProductType.valueOf(productRequestDTO.getType()))
                .build();
    }
}
