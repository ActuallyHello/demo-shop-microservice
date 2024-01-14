package com.happyfxmas.warehousemicroservice.api.mapper;

import com.happyfxmas.warehousemicroservice.api.dto.SupplierDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierRequestDTO;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import lombok.NonNull;

public class SupplierMapper {
    public static Supplier makeModel(@NonNull SupplierRequestDTO supplierRequestDTO) {
        return Supplier.builder()
                .companyName(supplierRequestDTO.getCompanyName())
                .email(supplierRequestDTO.getEmail())
                .phone(supplierRequestDTO.getPhone())
                .build();
    }

    public static SupplierDTO makeDTO(@NonNull Supplier supplier) {
        return SupplierDTO.builder()
                .id(supplier.getId())
                .companyName(supplier.getCompanyName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .createdAt(supplier.getCreatedAt())
                .updatedAt(supplier.getUpdatedAt())
                .build();
    }
}
