package com.happyfxmas.warehousemicroservice.service.impl;

import com.happyfxmas.warehousemicroservice.exception.service.ProductCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.ProductDeleteException;
import com.happyfxmas.warehousemicroservice.exception.service.ProductDoesNotExistException;
import com.happyfxmas.warehousemicroservice.service.ProductService;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.repository.ProductRepository;
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
    public Product getById(UUID id) {
        log.info("GET PRODUCT WITH ID={}", id);
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductDoesNotExistException("Product with id=%s was not found!".formatted(id)));
    }

    @Override
    public Product getByIdWithSupplier(UUID id) {
        log.info("GET PRODUCT WITH ID={}", id);
        return productRepository.findByIdWithSupplier(id)
                .orElseThrow(() ->
                        new ProductDoesNotExistException("Product with id=%s was not found!".formatted(id)));
    }

    @Override
    public Product getByIdWithInventory(UUID id) {
        log.info("GET PRODUCT WITH ID={}", id);
        return productRepository.findByIdWithInventory(id)
                .orElseThrow(() ->
                        new ProductDoesNotExistException("Product with id=%s was not found!".formatted(id)));
    }

    @Override
    @Transactional
    public Product create(Product product) {
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
    public Product update(Product newProduct) {
        try {
            newProduct = productRepository.saveAndFlush(newProduct);
            log.info("PRODUCT WAS UPDATED WITH ID={}", newProduct.getId());
            return newProduct;
        } catch (RuntimeException exception) {
            log.error("ERROR WHEN UPDATING PRODUCT WITH ID={}: {}", newProduct.getId(), exception.getMessage());
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
