package com.happyfxmas.ordermicroservice.store.repository.impl;

import com.happyfxmas.ordermicroservice.exception.service.OrderStatusCreationException;
import com.happyfxmas.ordermicroservice.exception.service.OrderStatusDeleteException;
import com.happyfxmas.ordermicroservice.store.mapper.ItemStatusMapper;
import com.happyfxmas.ordermicroservice.store.model.ItemStatus;
import com.happyfxmas.ordermicroservice.store.repository.ItemStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Repository
public class ItemStatusRepositoryImpl implements ItemStatusRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ItemStatusMapper itemStatusMapper;

    private static final String DATABASE_TAG = "[DATABASE]";

    private static final String SELECT_ITEM_STATUS_BY_ID = """
            SELECT item_status.id, item_status.code
            FROM item_status
            WHERE id=?;
            """;
    private static final String SELECT_ITEM_STATUS_BY_CODE = """
            SELECT item_status.id, item_status.code
            FROM item_status
            WHERE code=?;
            """;
    private static final String SELECT_ALL_ITEM_STATUSES = """
            SELECT item_status.id, item_status.code
            FROM item_status;
            """;
    private static final String INSERT_INTO_ITEM_STATUS = """
            INSERT INTO item_status (item_status.code)
            VALUES (?);
            """;
    private static final String UPDATE_ITEM_STATUS_BY_ID = """
            UPDATE item_status
            SET item_status.code=?
            WHERE item_status.id=?;
            """;
    private static final String DELETE_ITEM_STATUS_BY_ID = """
            DELETE FROM item_status
            WHERE item_status.id=?;
            """;

    @Override
    public Optional<ItemStatus> findById(Integer id) {
        var itemStatus = jdbcTemplate.query(
                SELECT_ITEM_STATUS_BY_ID,
                itemStatusMapper,
                id
        ).stream().findFirst();
        log.info("GET ITEM STATUS WITH ID={}", id);
        return itemStatus;
    }

    @Override
    public List<ItemStatus> findAll() {
        var itemStatuses = jdbcTemplate.query(
                SELECT_ALL_ITEM_STATUSES,
                itemStatusMapper);
        log.info("GET {} ITEM STATUSES", itemStatuses.size());
        return itemStatuses;
    }

    @Override
    public void save(ItemStatus orderStatus) {
        try {
            jdbcTemplate.update(
                    INSERT_INTO_ITEM_STATUS,
                    orderStatus.getCode());
            log.info("CREATED ITEM STATUS WITH CODE={}", orderStatus.getCode());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN SAVING ITEM STATUS: {}", exception.getMessage());
            throw new OrderStatusCreationException("Error when saving item status! " + DATABASE_TAG, exception);
        }
    }

    @Override
    public void update(Integer id, ItemStatus orderStatus) {
        try {
            jdbcTemplate.update(
                    UPDATE_ITEM_STATUS_BY_ID,
                    orderStatus.getCode(),
                    id);
            log.info("UPDATED ITEM STATUS WITH ID={}", id);
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN UPDATING ITEM STATUS: {}", exception.getMessage());
            throw new OrderStatusCreationException("Error when updating item status! " + DATABASE_TAG, exception);
        }
    }

    @Override
    public void delete(ItemStatus orderStatus) {
        try {
            jdbcTemplate.update(
                    DELETE_ITEM_STATUS_BY_ID,
                    orderStatus.getId());
            log.info("DELETED ITEM STATUS WITH ID={}", orderStatus.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN DELETING ITEM STATUS: {}", exception.getMessage());
            throw new OrderStatusDeleteException("Error when deleting item status! " + DATABASE_TAG, exception);
        }
    }

    @Override
    public Optional<ItemStatus> findByCode(String code) {
        var itemStatus = jdbcTemplate.query(
                SELECT_ITEM_STATUS_BY_CODE,
                itemStatusMapper,
                code
        ).stream().findFirst();
        log.info("GET ITEM STATUS WITH CODE={}", code);
        return itemStatus;
    }
}
