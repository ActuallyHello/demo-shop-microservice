package com.happyfxmas.warehousemicroservice.service;

import com.happyfxmas.warehousemicroservice.api.dto.request.ProductRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.ProductUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<Product> getAll(Pageable pageable);
    Product getById(UUID id);
    Product getByIdWithSupplier(UUID id);
    Product getByIdWithInventory(UUID id);
    Product create(Product product);
    Product update(Product newProduct);
    void delete(Product product);
}
