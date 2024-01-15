package com.happyfxmas.warehousemicroservice.store.repository;

import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    @Query("""
            select supplier
            from Supplier supplier
                left join fetch supplier.products
            where supplier.id = :id
            """)
    Optional<Supplier> findByIdWithProducts(UUID id);
}
