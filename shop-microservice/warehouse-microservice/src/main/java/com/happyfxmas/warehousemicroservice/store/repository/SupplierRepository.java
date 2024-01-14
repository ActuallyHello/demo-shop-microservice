package com.happyfxmas.warehousemicroservice.store.repository;

import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}
