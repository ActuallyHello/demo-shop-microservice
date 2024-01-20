package com.happyfxmas.ordermicroservice.store.repository.impl;

import com.happyfxmas.ordermicroservice.exception.service.OrderStatusCreationException;
import com.happyfxmas.ordermicroservice.exception.service.OrderStatusDeleteException;
import com.happyfxmas.ordermicroservice.store.mapper.OrderStatusMapper;
import com.happyfxmas.ordermicroservice.store.model.OrderStatus;
import com.happyfxmas.ordermicroservice.store.repository.OrderStatusRepository;
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
public class OrderStatusRepositoryImpl implements OrderStatusRepository {

    private final JdbcTemplate jdbcTemplate;
    private final OrderStatusMapper orderStatusMapper;

    private static final String DATABASE_TAG = "[DATABASE]";

    private static final String SELECT_ORDER_STATUS_BY_ID = """
            SELECT order_status.id, order_status.code
            FROM order_status
            WHERE id=?;
            """;
    private static final String SELECT_ORDER_STATUS_BY_CODE = """
            SELECT order_status.id, order_status.code
            FROM order_status
            WHERE code=?;
            """;
    private static final String SELECT_ALL_ORDER_STATUSES = """
            SELECT order_status.id, order_status.code
            FROM order_status;
            """;
    private static final String INSERT_INTO_ORDER_STATUS = """
            INSERT INTO order_status (order_status.code)
            VALUES (?);
            """;
    private static final String UPDATE_ORDER_STATUS_BY_ID = """
            UPDATE order_status
            SET order_status.code=?
            WHERE order_status.id=?;
            """;
    private static final String DELETE_ORDER_STATUS_BY_ID = """
            DELETE FROM order_status
            WHERE order_status.id=?;
            """;

    @Override
    public Optional<OrderStatus> findById(Integer id) {
        var orderStatus = jdbcTemplate.query(
                SELECT_ORDER_STATUS_BY_ID,
                orderStatusMapper,
                id
        ).stream().findFirst();
        log.info("GET ORDER STATUS WITH ID={}", id);
        return orderStatus;
    }

    @Override
    public List<OrderStatus> findAll() {
        var orderStatuses = jdbcTemplate.query(
                SELECT_ALL_ORDER_STATUSES,
                orderStatusMapper);
        log.info("GET {} ORDER STATUSES", orderStatuses.size());
        return orderStatuses;
    }

    @Override
    public void save(OrderStatus orderStatus) {
        try {
            jdbcTemplate.update(
                    INSERT_INTO_ORDER_STATUS,
                    orderStatus.getCode());
            log.info("CREATED ORDER STATUS WITH CODE={}", orderStatus.getCode());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN SAVING ORDER STATUS: {}", exception.getMessage());
            throw new OrderStatusCreationException("Error when saving order status! " + DATABASE_TAG, exception);
        }
    }

    @Override
    public void update(Integer id, OrderStatus orderStatus) {
        try {
            jdbcTemplate.update(
                    UPDATE_ORDER_STATUS_BY_ID,
                    orderStatus.getCode(),
                    id);
            log.info("UPDATED ORDER STATUS WITH ID={}", id);
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN UPDATING ORDER STATUS: {}", exception.getMessage());
            throw new OrderStatusCreationException("Error when updating order status! " + DATABASE_TAG, exception);
        }
    }

    @Override
    public void delete(OrderStatus orderStatus) {
        try {
            jdbcTemplate.update(
                    DELETE_ORDER_STATUS_BY_ID,
                    orderStatus.getId());
            log.info("DELETED ORDER STATUS WITH ID={}", orderStatus.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN DELETING ORDER STATUS: {}", exception.getMessage());
            throw new OrderStatusDeleteException("Error when deleting order status! " + DATABASE_TAG, exception);
        }
    }

    @Override
    public Optional<OrderStatus> findByCode(String code) {
        var orderStatus = jdbcTemplate.query(
                SELECT_ORDER_STATUS_BY_CODE,
                orderStatusMapper,
                code
        ).stream().findFirst();
        log.info("GET ORDER STATUS WITH CODE={}", code);
        return orderStatus;
    }
}
