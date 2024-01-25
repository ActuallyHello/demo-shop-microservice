package com.happyfxmas.warehousemicroservice.api.mapper;

import com.happyfxmas.warehousemicroservice.api.dto.SupplierDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.response.SupplierWithProductDTO;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import lombok.NonNull;

import java.util.Set;

public class SupplierDTOMapper {
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

    public static Supplier makeModel(@NonNull SupplierUpdateRequestDTO supplierUpdateRequestDTO, Supplier oldSupplier) {
        if (supplierUpdateRequestDTO.getCompanyName() == null && supplierUpdateRequestDTO.getPhone() == null &&
                supplierUpdateRequestDTO.getEmail() == null) {
            throw new IllegalArgumentException("There are no fields to update in supplier!");
        }
        return Supplier.builder()
                .id(oldSupplier.getId())
                .createdAt(oldSupplier.getCreatedAt())
                .updatedAt(oldSupplier.getUpdatedAt())
                .products(oldSupplier.getProducts())
                .phone(supplierUpdateRequestDTO.getPhone() != null
                        ? supplierUpdateRequestDTO.getPhone()
                        : oldSupplier.getPhone())
                .companyName(supplierUpdateRequestDTO.getCompanyName() != null
                        ? supplierUpdateRequestDTO.getCompanyName()
                        : oldSupplier.getCompanyName())
                .email(supplierUpdateRequestDTO.getPhone() != null
                        ? supplierUpdateRequestDTO.getEmail()
                        : oldSupplier.getEmail())
                .build();
    }

    public static SupplierWithProductDTO makeDTO(Supplier supplier, Set<Product> products) {
        var productDTOs = products.stream()
                .map(ProductDTOMapper::makeDTO)
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
