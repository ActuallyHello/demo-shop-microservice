package com.happyfxmas.warehousemicroservice.service.impl;

import com.happyfxmas.warehousemicroservice.api.dto.request.ProductRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.ProductUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.api.mapper.ProductMapper;
import com.happyfxmas.warehousemicroservice.exception.service.ProductCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.ProductDeleteException;
import com.happyfxmas.warehousemicroservice.service.ProductService;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import com.happyfxmas.warehousemicroservice.store.model.enums.ProductType;
import com.happyfxmas.warehousemicroservice.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final String LOG_DB_ERROR_TAG = "[DATABASE]";

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
    public Optional<Product> getByIdWithInventory(UUID id) {
        var product = productRepository.findByIdWithInventory(id);
        log.info("GET PRODUCT WITH ID={}", id);
        return product;
    }

    @Override
    @Transactional
    public Product create(ProductRequestDTO productRequestDTO, Supplier supplier) {
        var product = ProductMapper.makeModel(productRequestDTO);
        product.setSupplier(supplier);
        try {
            product = productRepository.saveAndFlush(product);
            log.info("PRODUCT WAS CREATED WITH ID={}", product.getId());
            return product;
        } catch (RuntimeException exception) {
            log.error("ERROR WHEN CREATING PRODUCT: {}", exception.getMessage());
            throw new ProductCreationException("Error when creating product! " + LOG_DB_ERROR_TAG, exception);
        }
    }

    @Override
    @Transactional
    public Product update(ProductUpdateRequestDTO productUpdateRequestDTO, Product product) {
        if (productUpdateRequestDTO.getTitle() != null) {
            product.setTitle(productUpdateRequestDTO.getTitle());
        }
        if (productUpdateRequestDTO.getDescription() != null) {
            product.setDescription(productUpdateRequestDTO.getDescription());
        }
        if (productUpdateRequestDTO.getType() != null) {
            product.setType(ProductType.valueOf(productUpdateRequestDTO.getType()));
        }
        try {
            product = productRepository.saveAndFlush(product);
            log.info("PRODUCT WAS UPDATED WITH ID={}", product.getId());
            return product;
        } catch (RuntimeException exception) {
            log.error("ERROR WHEN UPDATING PRODUCT WITH ID={}: {}", product.getId(), exception.getMessage());
            throw new ProductCreationException("Error when updating product! " + LOG_DB_ERROR_TAG, exception);
        }
    }

    @Override
    @Transactional
    public void delete(Product product) {
        try {
            productRepository.delete(product);
            productRepository.flush();
            log.info("PRODUCT WAS DELETED WITH ID={}", product.getId());
        } catch (RuntimeException exception) {
            log.error("ERROR WHEN DELETING PRODUCT WITH ID={}: {}", product.getId(), exception.getMessage());
            throw new ProductDeleteException("Error when deleting product! " + LOG_DB_ERROR_TAG, exception);
        }
    }

}
