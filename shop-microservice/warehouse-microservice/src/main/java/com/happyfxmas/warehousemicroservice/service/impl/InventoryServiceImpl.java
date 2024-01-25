package com.happyfxmas.warehousemicroservice.service.impl;

import com.happyfxmas.warehousemicroservice.exception.service.InventoryCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.InventoryDeleteException;
import com.happyfxmas.warehousemicroservice.exception.service.InventoryDoesNotExistException;
import com.happyfxmas.warehousemicroservice.service.InventoryService;
import com.happyfxmas.warehousemicroservice.store.model.Inventory;
import com.happyfxmas.warehousemicroservice.store.model.Product;
import com.happyfxmas.warehousemicroservice.store.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private static final String LOG_DB_ERROR_TAG = "[DATABASE]";

    @Override
    public Inventory getById(UUID id) {
        log.info("GET INVENTORY WITH ID={}", id);
        return inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new InventoryDoesNotExistException("Inventory with id=%s was not found!".formatted(id)));
    }

    @Override
    public Inventory getByProduct(Product product) {
        log.info("GET INVENTORY BY PRODUCT WITH ID={}", product.getId());
        return inventoryRepository.findByProduct(product)
                .orElseThrow(() ->
                        new InventoryDoesNotExistException(
                                "Inventory with product id=%s was not found!".formatted(product.getId())));
    }

    @Override
    @Transactional
    public Inventory create(Inventory inventory) {
        try {
            inventory = inventoryRepository.saveAndFlush(inventory);
            log.info("INVENTORY WAS CREATED WITH ID={}", inventory.getId());
            return inventory;
        } catch (RuntimeException exception) { // TODO CATCH COMMON FOR DataIntegrityViolationException AND PersistenceException
            log.error("ERROR WHEN CREATING INVENTORY: {}", exception.getMessage());
            throw new InventoryCreationException("Error when creating inventory! " + LOG_DB_ERROR_TAG, exception);
        }
    }

    @Override
    @Transactional
    public Inventory update(Inventory newInventory) {
        try {
            newInventory = inventoryRepository.saveAndFlush(newInventory);
            log.info("PRODUCT WAS UPDATED WITH ID={}", newInventory.getId());
            return newInventory;
        } catch (RuntimeException exception) { // TODO
            log.error("ERROR WHEN UPDATING PRODUCT WITH ID={}: {}", newInventory.getId(), exception.getMessage());
            throw new InventoryCreationException("Error when updating inventory! " + LOG_DB_ERROR_TAG, exception);
        }
    }

    @Override
    @Transactional
    public void delete(Inventory inventory) {
        try {
            inventoryRepository.delete(inventory);
            inventoryRepository.flush();
            log.info("INVENTORY WAS DELETED WITH ID={}", inventory.getId());
        } catch (RuntimeException exception) { // TODO
            log.error("ERROR WHEN DELETING INVENTORY WITH ID={}: {}", inventory.getId(), exception.getMessage());
            throw new InventoryDeleteException("Error when deleting inventory! " + LOG_DB_ERROR_TAG, exception);
        }
    }
}
