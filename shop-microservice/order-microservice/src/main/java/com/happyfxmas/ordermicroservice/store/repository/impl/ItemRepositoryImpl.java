package com.happyfxmas.ordermicroservice.store.repository.impl;

import com.happyfxmas.ordermicroservice.exception.service.ItemCreationException;
import com.happyfxmas.ordermicroservice.exception.service.ItemDeleteException;
import com.happyfxmas.ordermicroservice.store.mapper.ItemWithStatusMapper;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ItemWithStatusMapper itemWithStatusMapper;

    private static final String DATABASE_TAG = "[DATABASE]";

    private static final String SELECT_ITEM_WITH_STATUS_BY_ID = """
            SELECT item.id, item.created_at,
                   item.updated_at, item.product_id,
                   item.price, item.quantity, item.orders_id,
                   item_status.id, item_status.code
            FROM item
            INNER JOIN item_status
                ON item_status.id = item.item_status_id
            WHERE item.id = ?
            ORDER BY item.updated_at;
            """;
    private static final String SELECT_ITEMS_WITH_STATUS_BY_ORDER_ID = """
            SELECT item.id, item.created_at,
                   item.updated_at, item.product_id,
                   item.price, item.quantity, item.orders_id,
                   item_status.id, item_status.code
            FROM item
            INNER JOIN item_status
                ON item_status.id = item.item_status_id
            WHERE item.order_id = ?
            ORDER BY item.updated_at;
            """;
    private static final String SELECT_ITEMS_WITH_STATUS = """
            SELECT item.id, item.created_at,
                   item.updated_at, item.product_id,
                   item.price, item.quantity, item.orders_id,
                   item_status.id, item_status.code
            FROM item
            INNER JOIN item_status
                ON item_status.id = item.item_status_id
            ORDER BY item.updated_at;
            """;
    private static final String INSERT_INTO_ITEM = """
            INSERT INTO orders (item.id, item.created_at,
                                item.updated_at, item.product_id,
                                item.price, item.quantity,
                                item.orders_id, item.item_status_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;
    private static final String UPDATE_ITEM_BY_ID = """
            UPDATE item
            SET updated_at=?, product_id=?, price=?, quantity=?, order_id=?, item_status_id=?
            WHERE item.id=?;
            """;
    private static final String DELETE_ITEM_BY_ID = """
            DELETE FROM item
            WHERE item.id=?;
            """;

    @Override
    public Optional<Item> findById(UUID id) {
        var order = jdbcTemplate.query(
                SELECT_ITEM_WITH_STATUS_BY_ID,
                itemWithStatusMapper,
                id
        ).stream().findFirst();
        log.info("GET ITEM BY ID={}", id);
        return order;
    }

    @Override
    public List<Item> findAll() {
        var items = jdbcTemplate.query(
                SELECT_ITEMS_WITH_STATUS,
                itemWithStatusMapper);
        log.info("GET {} ITEMS", items.size());
        return items;
    }

    @Override
    public void save(Item item) {
        try {
            jdbcTemplate.update(
                    INSERT_INTO_ITEM,
                    item.getId(),
                    item.getCreatedAt(),
                    item.getUpdatedAt(),
                    item.getProductId(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getOrder().getId(),
                    item.getItemStatus().getId());
            log.info("CREATED ITEM WITH ID={}", item.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN SAVING ITEM: {}", exception.getMessage());
            throw new ItemCreationException("Error when saving item! " + DATABASE_TAG);
        }
    }

    @Override
    public void update(UUID id, Item item) {
        try {
            jdbcTemplate.update(
                    UPDATE_ITEM_BY_ID,
                    item.getUpdatedAt(),
                    item.getProductId(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getOrder().getId(),
                    item.getItemStatus().getId(),
                    id);
            log.info("UPDATED ITEM WITH ID={}", item.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN UPDATING ITEM: {}", exception.getMessage());
            throw new ItemCreationException("Error when updating item! " + DATABASE_TAG);
        }
    }

    @Override
    public void delete(Item item) {
        try {
            jdbcTemplate.update(DELETE_ITEM_BY_ID, item.getId());
            log.info("DELETED ITEM WITH ID={}", item.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN DELETING ITEM: {}", exception.getMessage());
            throw new ItemDeleteException("Error when deleting item! " + DATABASE_TAG);
        }
    }

    @Override
    public List<Item> findAllByOrderId(UUID orderId) {
        var items = jdbcTemplate.query(
                SELECT_ITEMS_WITH_STATUS_BY_ORDER_ID,
                itemWithStatusMapper,
                orderId);
        log.info("GET {} ITEMS", items.size());
        return items;
    }
}
