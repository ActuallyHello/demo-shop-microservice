package com.happyfxmas.warehousemicroservice.api.mapper;

import com.happyfxmas.warehousemicroservice.api.dto.response.SupplierWithProductDTO;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;

import java.util.Set;

public class SupplierWithProductMapper {
    public static SupplierWithProductDTO makeDTO(Supplier supplier, Set<Product> products) {
        var productDTOs = products.stream()
                .map(ProductMapper::makeDTO)
                .toList();
        return SupplierWithProductDTO.builder()
                .id(supplier.getId())
                .companyName(supplier.getCompanyName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .createdAt(supplier.getCreatedAt())
                .updatedAt(supplier.getUpdatedAt())
                .productDTOs(productDTOs)
                .build();
    }
}
