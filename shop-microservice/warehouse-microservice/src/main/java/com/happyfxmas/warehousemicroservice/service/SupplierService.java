package com.happyfxmas.warehousemicroservice.service;

import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SupplierService {
    List<Supplier> getAll(Pageable pageable);
    Supplier getById(UUID id);
    Supplier getByIdWithProducts(UUID id);
    Supplier create(Supplier supplier);
    Supplier update(Supplier newSupplier);
    void delete(Supplier supplier);
}
