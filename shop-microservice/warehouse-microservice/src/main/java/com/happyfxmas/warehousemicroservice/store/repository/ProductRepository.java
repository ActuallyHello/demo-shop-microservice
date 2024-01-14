package com.happyfxmas.warehousemicroservice.store.repository;

import com.happyfxmas.warehousemicroservice.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("""
            select product
            from Product product
                inner join fetch product.supplier
            """)
    Optional<Product> findByIdWithSupplier(UUID id);
}
