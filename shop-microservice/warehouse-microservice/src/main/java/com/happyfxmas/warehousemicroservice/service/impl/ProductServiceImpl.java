package com.happyfxmas.warehousemicroservice.service.impl;

import com.happyfxmas.warehousemicroservice.api.dto.request.ProductRequestDTO;
import com.happyfxmas.warehousemicroservice.api.mapper.ProductMapper;
import com.happyfxmas.warehousemicroservice.exception.service.ProductCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.ProductDeleteException;
import com.happyfxmas.warehousemicroservice.service.ProductService;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import com.happyfxmas.warehousemicroservice.store.repository.ProductRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll(Pageable pageable) {
        var products = productRepository.findAll(pageable).toList();
        log.info("GET {} PRODUCTS", products.size());
        return products;
    }

    @Override
    public Optional<Product> getById(UUID id) {
        var product = productRepository.findById(id);
        log.info("GET PRODUCT WITH ID={}", id);
        return product;
    }

    @Override
    public Optional<Product> getByIdWithSupplier(UUID id) {
        var product = productRepository.findByIdWithSupplier(id);
        log.info("GET PRODUCT WITH ID={}", id);
        return product;
    }

    @Override
    @Transactional
    public Product create(ProductRequestDTO productRequestDTO, Supplier supplier) {
        var product = ProductMapper.makeModel(productRequestDTO);
        try {
            product = productRepository.save(product);
            log.info("PRODUCT WAS CREATED WITH ID={}", product.getId());
            return product;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING PRODUCT: {}", exception.getMessage());
            throw new ProductCreationException("Error when creating product! [DATABASE]", exception);
        }
    }

    @Override
    @Transactional
    public Product update(Product product) {
        try {
            product = productRepository.saveAndFlush(product);
            log.info("PRODUCT WAS UPDATED WITH ID={}", product.getId());
            return product;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN UPDATING PRODUCT: {}", exception.getMessage());
            throw new ProductCreationException("Error when updating product! [DATABASE]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Product product) {
        try {
            productRepository.delete(product);
            productRepository.flush();
            log.info("PRODUCT WAS DELETED WITH ID={}", product.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING PRODUCT: {}", exception.getMessage());
            throw new ProductDeleteException("Error when deleting product! [DATABASE]", exception);
        }
    }

}
