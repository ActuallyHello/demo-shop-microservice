package com.happyfxmas.warehousemicroservice.service.impl;

import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierRequestDTO;
import com.happyfxmas.warehousemicroservice.api.mapper.SupplierMapper;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierDeleteException;
import com.happyfxmas.warehousemicroservice.service.SupplierService;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import com.happyfxmas.warehousemicroservice.store.repository.SupplierRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
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
    public List<Supplier> getAll(Pageable pageable) {
        var suppliers = supplierRepository.findAll(pageable).toList();
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
            supplier = supplierRepository.saveAndFlush(supplier);
            log.info("SUPPLIER WAS CREATED WITH ID={}", supplier.getId());
            return supplier;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING SUPPLIER: {}", exception.getMessage());
            throw new SupplierCreationException("Error when creating supplier! [DATABASE]", exception);
        }
    }

    @Override
    @Transactional
    public Supplier update(Supplier supplier) {
        try {
            supplier = supplierRepository.saveAndFlush(supplier);
            log.info("SUPPLIER WITH ID={} WAS UPDATED", supplier.getId());
            return supplier;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN UPDATING SUPPLIER WITH ID={}: {}", supplier.getId(), exception.getMessage());
            throw new SupplierCreationException("Error when updating supplier! [DATABASE]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Supplier supplier) {
        try {
            supplierRepository.delete(supplier);
            supplierRepository.flush();
            log.info("SUPPLIER WITH ID={} WAS DELETED!", supplier.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING SUPPLIER WITH ID={}: {}", supplier.getId(), exception.getMessage());
            throw new SupplierDeleteException("Error when deleting supplier! [DATABASE]", exception);
        }
    }
}
