package com.happyfxmas.warehousemicroservice.service;

import com.happyfxmas.warehousemicroservice.api.dto.request.ProductRequestDTO;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<Product> getAll(Pageable pageable);
    Optional<Product> getById(UUID id);
    Optional<Product> getByIdWithSupplier(UUID id);
    Product create(ProductRequestDTO productRequestDTO, Supplier supplier);
    Product update(Product product);
    void delete(Product product);
}
