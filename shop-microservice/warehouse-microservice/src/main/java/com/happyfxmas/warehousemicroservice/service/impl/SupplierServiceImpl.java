package com.happyfxmas.warehousemicroservice.service.impl;

import com.happyfxmas.warehousemicroservice.exception.response.SupplierServerException;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierDeleteException;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierDoesNotExistException;
import com.happyfxmas.warehousemicroservice.service.SupplierService;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import com.happyfxmas.warehousemicroservice.store.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private static final String LOG_DB_ERROR_TAG = "[DATABASE]";

    @Override
    public List<Supplier> getAll(Pageable pageable) {
        var suppliers = supplierRepository.findAll(pageable).toList();
        log.info("GET {} SUPPLIERS", suppliers.size());
        return suppliers;
    }

    @Override
    public Supplier getById(UUID id) {
        log.info("GET SUPPLIER WITH ID={}", id);
        return supplierRepository.findById(id)
                .orElseThrow(() ->
                        new SupplierDoesNotExistException("Supplier with id=%s was not found!".formatted(id)));
    }

    @Override
    public Supplier getByIdWithProducts(UUID id) {
        log.info("GET SUPPLIER WITH ID={}", id);
        return supplierRepository.findByIdWithProducts(id)
                .orElseThrow(() ->
                        new SupplierDoesNotExistException("Supplier with id=%s was not found!".formatted(id)));
    }

    @Override
    @Transactional
    public Supplier create(Supplier supplier) {
        try {
            supplier = supplierRepository.saveAndFlush(supplier);
            log.info("SUPPLIER WAS CREATED WITH ID={}", supplier.getId());
            return supplier;
        } catch (RuntimeException exception) {
            log.error("ERROR WHEN CREATING SUPPLIER: {}", exception.getMessage());
            throw new SupplierServerException("Error when creating supplier! " + LOG_DB_ERROR_TAG, exception);
        }
    }

    @Override
    @Transactional
    public Supplier update(Supplier newSupplier) {
        try {
            newSupplier = supplierRepository.saveAndFlush(newSupplier);
            log.info("SUPPLIER WITH ID={} WAS UPDATED", newSupplier.getId());
            return newSupplier;
        } catch (RuntimeException exception) {
            log.error("ERROR WHEN UPDATING SUPPLIER WITH ID={}: {}", newSupplier.getId(), exception.getMessage());
            throw new SupplierCreationException("Error when updating supplier! " + LOG_DB_ERROR_TAG, exception);
        }
    }

    @Override
    @Transactional
    public void delete(Supplier supplier) {
        try {
            supplierRepository.delete(supplier);
            supplierRepository.flush();
            log.info("SUPPLIER WITH ID={} WAS DELETED!", supplier.getId());
        } catch (RuntimeException exception) {
            log.error("ERROR WHEN DELETING SUPPLIER WITH ID={}: {}", supplier.getId(), exception.getMessage());
            throw new SupplierDeleteException("Error when deleting supplier! " + LOG_DB_ERROR_TAG, exception);
        }
    }
}
