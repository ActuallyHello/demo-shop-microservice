package com.happyfxmas.ordermicroservice.service.impl;

import com.happyfxmas.ordermicroservice.exception.service.ItemCreationException;
import com.happyfxmas.ordermicroservice.exception.service.ItemDeleteException;
import com.happyfxmas.ordermicroservice.exception.service.ItemDoesNotExistException;
import com.happyfxmas.ordermicroservice.service.ItemService;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private static final String DATABASE_TAG = "[DATABASE]";

    @Override
    public Item getById(UUID id) {
        log.info("GET ITEM WITH ID={}", id);
        return itemRepository.findById(id)
                .orElseThrow( () -> {
                        log.warn("ITEM WITH ID={} DOES NOT EXIST!", id);
                        return new ItemDoesNotExistException("Item with id=%s was not found!".formatted(id));
                });
    }

    @Override
    public List<Item> getAllByOrderId(UUID orderId) {
        log.info("GET ITEMS BY ORDER ID={}", orderId);
        return itemRepository.findAllByOrderId(orderId);
    }

    @Override
    public Item create(Item item) {
        try {
            itemRepository.save(item);
            log.info("CREATED ITEM WITH ID={}", item.getId());
            return item;
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN SAVING ITEM: {}", exception.getMessage());
            throw new ItemCreationException("Error when saving item! " + DATABASE_TAG);
        }
    }

    @Override
    public List<Item> createAll(List<Item> items) {
        try {
            itemRepository.saveAll(items);
            log.info("CREATED {} ITEMS", items.size());
            return items;
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN SAVING ITEM: {}", exception.getMessage());
            throw new ItemCreationException("Error when saving item! " + DATABASE_TAG);
        }
    }

    @Override
    public void update(Item newItem) {
        try {
            itemRepository.update(newItem);
            log.info("UPDATED ITEM WITH ID={}", newItem.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN UPDATING ITEM: {}", exception.getMessage());
            throw new ItemCreationException("Error when updating item! " + DATABASE_TAG);
        }
    }

    @Override
    public void delete(Item item) {
        try {
            itemRepository.delete(item);
            log.info("DELETED ITEM WITH ID={}", item.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN DELETING ITEM: {}", exception.getMessage());
            throw new ItemDeleteException("Error when deleting item! " + DATABASE_TAG);
        }
    }
}
