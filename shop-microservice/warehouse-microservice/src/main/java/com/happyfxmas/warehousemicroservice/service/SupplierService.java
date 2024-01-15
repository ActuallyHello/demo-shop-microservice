package com.happyfxmas.warehousemicroservice.service;

import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierRequestDTO;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierService {
    List<Supplier> getAll(Pageable pageable);
    Optional<Supplier> getById(UUID id);
    Supplier create(SupplierRequestDTO supplierRequestDTO);
    Supplier update(Supplier supplier);
    void delete(Supplier supplier);
}
