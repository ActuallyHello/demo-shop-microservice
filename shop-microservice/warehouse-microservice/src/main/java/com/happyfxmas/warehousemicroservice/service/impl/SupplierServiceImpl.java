package com.happyfxmas.warehousemicroservice.service.impl;

import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierRequestDTO;
import com.happyfxmas.warehousemicroservice.api.mapper.SupplierMapper;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierCreationException;
import com.happyfxmas.warehousemicroservice.service.SupplierService;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import com.happyfxmas.warehousemicroservice.store.repository.SupplierRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        var suppliers = supplierRepository.findAll();
        log.info("GET {} suppliers", suppliers.size());
        return suppliers;
    }

    @Override
    public Optional<Supplier> getById(UUID id) {
        var supplier = supplierRepository.findById(id);
        log.info("GET SUPPLIER WITH ID={}", id);
        return supplier;
    }

    @Override
    @Transactional
    public Supplier create(SupplierRequestDTO supplierRequestDTO) {
        var supplier = SupplierMapper.makeModel(supplierRequestDTO);
        try {
            supplier = supplierRepository.save(supplier);
            log.info("SUPPLIER WAS CREATED WITH ID={}", supplier.getId());
            return supplier;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING SUPPLIER: {}", exception.getMessage());
            throw new SupplierCreationException("Error when creating supplier! [DATABASE]", exception);
        }
    }
}
