package com.happyfxmas.warehousemicroservice.api.mapper;

import com.happyfxmas.warehousemicroservice.api.dto.ProductDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.ProductRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.ProductUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.response.ProductWithInventoryDTO;
import com.happyfxmas.warehousemicroservice.api.dto.response.ProductWithSupplierDTO;
import com.happyfxmas.warehousemicroservice.store.model.Inventory;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import com.happyfxmas.warehousemicroservice.store.model.enums.ProductType;
import lombok.NonNull;

public class ProductDTOMapper {
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

    public static Product makeModel(@NonNull ProductRequestDTO productRequestDTO, Supplier supplier) {
        return Product.builder()
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .type(ProductType.valueOf(productRequestDTO.getType()))
                .supplier(supplier)
                .build();
    }

    public static Product makeModel(ProductUpdateRequestDTO productUpdateRequestDTO, Product oldProduct) {
        if (productUpdateRequestDTO.getType() == null && productUpdateRequestDTO.getDescription() == null &&
                productUpdateRequestDTO.getTitle() == null) {
            throw new IllegalArgumentException("There are no fields to update in product!");
        }
        return Product.builder()
                .id(oldProduct.getId())
                .supplier(oldProduct.getSupplier())
                .createdAt(oldProduct.getCreatedAt())
                .updatedAt(oldProduct.getUpdatedAt())
                .inventory(oldProduct.getInventory())
                .title(productUpdateRequestDTO.getTitle() != null
                        ? productUpdateRequestDTO.getTitle()
                        : oldProduct.getTitle())
                .type(productUpdateRequestDTO.getType() != null
                        ? ProductType.valueOf(productUpdateRequestDTO.getType())
                        : oldProduct.getType())
                .description(productUpdateRequestDTO.getDescription() != null
                        ? productUpdateRequestDTO.getDescription()
                        : oldProduct.getDescription())
                .build();
    }

    public static ProductWithSupplierDTO makeDTO(@NonNull Product product, @NonNull Supplier supplier) {
        return ProductWithSupplierDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .type(product.getType())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .supplierId(product.getSupplier().getId())
                .supplierDTO(SupplierDTOMapper.makeDTO(supplier))
                .build();
    }

    public static ProductWithInventoryDTO makeDTO(@NonNull Product product, Inventory inventory) {
        var inventoryDTO = inventory != null
                ? InventoryDTOMapper.makeDTO(inventory)
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
