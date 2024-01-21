package com.happyfxmas.ordermicroservice.store.repository.impl;

import com.happyfxmas.ordermicroservice.store.mapper.ItemMapper;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ItemMapper itemWithStatusMapper;

    private static final String DATABASE_TAG = "[DATABASE]";

    private static final String SELECT_ITEM_BY_ID = """
            SELECT item.id, item.created_at,
                   item.updated_at, item.product_id,
                   item.price, item.quantity, item.orders_id,
                   item_status.id, item.status
            FROM item
            WHERE item.id = ?
            ORDER BY item.updated_at;
            """;
    private static final String SELECT_ITEMS_BY_ORDER_ID = """
            SELECT item.id, item.created_at,
                   item.updated_at, item.product_id,
                   item.price, item.quantity, item.orders_id,
                   item_status.id, item.status
            FROM item
            WHERE item.order_id = ?
            ORDER BY item.updated_at;
            """;
    private static final String SELECT_ITEMS = """
            SELECT item.id, item.created_at,
                   item.updated_at, item.product_id,
                   item.price, item.quantity, item.orders_id,
                   item_status.id, item.status
            FROM item
            ORDER BY item.updated_at;
            """;
    private static final String INSERT_INTO_ITEM = """
            INSERT INTO orders (id, created_at,
                                updated_at, product_id,
                                price, quantity,
                                orders_id, item.status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;
    private static final String UPDATE_ITEM_BY_ID = """
            UPDATE item
            SET updated_at=?, product_id=?, price=?, quantity=?, order_id=?, status=?
            WHERE item.id=?;
            """;
    private static final String DELETE_ITEM_BY_ID = """
            DELETE FROM item
            WHERE item.id=?;
            """;

    @Override
    public Optional<Item> findById(UUID id) {
        return jdbcTemplate.query(
                SELECT_ITEM_BY_ID,
                itemWithStatusMapper,
                id
        ).stream().findFirst();
    }

    @Override
    public List<Item> findAll() {
        return jdbcTemplate.query(
                SELECT_ITEMS,
                itemWithStatusMapper);
    }

    @Override
    public void save(Item item) {
        jdbcTemplate.update(
                INSERT_INTO_ITEM,
                item.getId(),
                item.getCreatedAt(),
                item.getUpdatedAt(),
                item.getProductId(),
                item.getPrice(),
                item.getQuantity(),
                item.getOrder().getId(),
                item.getItemStatus().getStatus());

    }

    @Override
    public void update(Item item) {
        jdbcTemplate.update(
                UPDATE_ITEM_BY_ID,
                item.getUpdatedAt(),
                item.getProductId(),
                item.getPrice(),
                item.getQuantity(),
                item.getOrder().getId(),
                item.getItemStatus().getStatus(),
                item.getId());
    }

    @Override
    public void delete(Item item) {
        jdbcTemplate.update(DELETE_ITEM_BY_ID, item.getId());
    }

    @Override
    public List<Item> saveAll(List<Item> items) {
        jdbcTemplate.batchUpdate(INSERT_INTO_ITEM, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Item item = items.get(i);
                ps.setObject(1, item.getId());
                ps.setTimestamp(2, item.getCreatedAt());
                ps.setTimestamp(3, item.getUpdatedAt());
                ps.setObject(4, item.getProductId());
                ps.setBigDecimal(5, item.getPrice());
                ps.setObject(6, item.getQuantity());
                ps.setObject(7, item.getOrder().getId());
                ps.setString(8, item.getItemStatus().getStatus());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
        return items;
    }

    @Override
    public List<Item> findAllByOrderId(UUID orderId) {
        return jdbcTemplate.query(
                SELECT_ITEMS_BY_ORDER_ID,
                itemWithStatusMapper,
                orderId);
    }
}
