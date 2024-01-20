package com.happyfxmas.ordermicroservice.store.repository.impl;

import com.happyfxmas.ordermicroservice.exception.service.OrderCreationException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDeleteException;
import com.happyfxmas.ordermicroservice.store.mapper.OrderWithStatusMapper;
import com.happyfxmas.ordermicroservice.store.model.Order;
import com.happyfxmas.ordermicroservice.store.model.OrderStatus;
import com.happyfxmas.ordermicroservice.store.repository.OrderRepository;
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
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final OrderWithStatusMapper orderWithStatusMapper;

    private static final String DATABASE_TAG = "[DATABASE]";

    private static final String SELECT_ORDER_WITH_STATUS_BY_ID = """
            SELECT order.id, order.created_at,
                   order.updated_at, order.customer_id,
                   order.total_amount, order_status.id, order_status.code
            FROM order
            INNER JOIN order_status
                ON order_status.id = order.order_status_id
            WHERE order.id = ?
            ORDER BY order.updated_at;
            """;
    private static final String SELECT_ORDERS_WITH_STATUS_BY_CUSTOMER_ID = """
            SELECT order.id, order.created_at,
                   order.updated_at, order.customer_id,
                   order.total_amount, order_status.id, order_status.code
            FROM order
            INNER JOIN order_status
                ON order_status.id = order.order_status_id
            WHERE order.customer_id = ?
            ORDER BY order.updated_at;
            """;
    private static final String SELECT_ORDERS_WITH_STATUS = """
            SELECT order.id, order.created_at,
                   order.updated_at, order.customer_id,
                   order.total_amount, order_status.id, order_status.code
            FROM order
            INNER JOIN order_status
                ON order_status.id = order.order_status_id
            ORDER BY order.updated_at;
            """;
    private static final String SELECT_ORDERS_WITH_STATUS_BY_ORDER_STATUS_ID = """
            SELECT order.id, order.created_at,
                   order.updated_at, order.customer_id,
                   order.total_amount, order_status.id, order_status.code
            FROM order
            INNER JOIN order_status
                ON order_status.id = order.order_status_id
            WHERE order_status.id=?
            ORDER BY order.updated_at;
            """;
    private static final String INSERT_INTO_ORDER = """
            INSERT INTO orders (order.id, order.created_at,
                                order.updated_at, order.customer_id,
                                order.total_amount, order.order_status_id)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    private static final String UPDATE_ORDER_BY_ID = """
            UPDATE orders
            SET updated_at=?, customer_id=?, total_amount=?, order_status_id=?
            WHERE orders.id=?;
            """;
    private static final String DELETE_ORDER_BY_ID = """
            DELETE FROM orders
            WHERE orders.id=?;
            """;

    @Override
    public Optional<Order> findById(UUID id) {
        var order = jdbcTemplate.query(
                SELECT_ORDER_WITH_STATUS_BY_ID,
                orderWithStatusMapper,
                id
        ).stream().findFirst();
        log.info("GET ORDER BY ID={}", id);
        return order;
    }

    @Override
    public List<Order> findAll() {
        var orders = jdbcTemplate.query(
                SELECT_ORDERS_WITH_STATUS,
                orderWithStatusMapper);
        log.info("GET {} ORDERS", orders.size());
        return orders;
    }

    @Override
    public void save(Order order) {
        try {
            jdbcTemplate.update(
                    INSERT_INTO_ORDER,
                    order.getId(),
                    order.getCreatedAt(),
                    order.getUpdatedAt(),
                    order.getCustomerId(),
                    order.getTotalAmount(),
                    order.getOrderStatus().getId());
            log.info("CREATED ORDER WITH ID={}", order.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN SAVING ORDER: {}", exception.getMessage());
            throw new OrderCreationException("Error when saving order! " + DATABASE_TAG);
        }
    }

    @Override
    public void update(UUID id, Order order) {
        try {
            jdbcTemplate.update(
                    UPDATE_ORDER_BY_ID,
                    order.getUpdatedAt(),
                    order.getCustomerId(),
                    order.getTotalAmount(),
                    order.getOrderStatus().getId(),
                    id);
            log.info("UPDATED ORDER WITH ID={}", order.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN UPDATING ORDER: {}", exception.getMessage());
            throw new OrderCreationException("Error when updating order! " + DATABASE_TAG);
        }
    }

    @Override
    public void delete(Order order) {
        try {
            jdbcTemplate.update(DELETE_ORDER_BY_ID, order.getId());
            log.info("DELETED ORDER WITH ID={}", order.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN DELETING ORDER: {}", exception.getMessage());
            throw new OrderDeleteException("Error when deleting order! " + DATABASE_TAG);
        }
    }

    @Override
    public List<Order> findAllByCustomerId(UUID customerId) {
        var order = jdbcTemplate.query(
                SELECT_ORDERS_WITH_STATUS_BY_CUSTOMER_ID,
                orderWithStatusMapper,
                customerId);
        log.info("GET {} ORDERS BY CUSTOMER ID={}", order.size(), customerId);
        return order;
    }

    @Override
    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        var orders = jdbcTemplate.query(
                SELECT_ORDERS_WITH_STATUS_BY_ORDER_STATUS_ID,
                orderWithStatusMapper,
                orderStatus.getId());
        log.info("GET {} ORDERS BY ORDER STATUS ID={}", orders.size(), orderStatus.getId());
        return orders;
    }
}
