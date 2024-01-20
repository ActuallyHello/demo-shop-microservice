package com.happyfxmas.ordermicroservice.store.repository.impl;

import com.happyfxmas.ordermicroservice.exception.service.OrderCreationException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDeleteException;
import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.mapper.OrderMapper;
import com.happyfxmas.ordermicroservice.store.model.Order;
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
    private final OrderMapper orderMapper;

    private static final String DATABASE_TAG = "[DATABASE]";

    private static final String SELECT_ORDER_BY_ID = """
            SELECT orders.id, orders.created_at,
                   orders.updated_at, orders.customer_id,
                   orders.total_amount, orders.status
            FROM orders
            WHERE orders.id = ?
            ORDER BY orders.updated_at;
            """;
    private static final String SELECT_ORDERS_BY_CUSTOMER_ID = """
            SELECT orders.id, orders.created_at,
                   orders.updated_at, orders.customer_id,
                   orders.total_amount, orders.status
            FROM orders
            WHERE orders.customer_id = ?
            ORDER BY orders.updated_at;
            """;
    private static final String SELECT_ORDERS = """
            SELECT orders.id, orders.created_at,
                   orders.updated_at, orders.customer_id,
                   orders.total_amount, orders.status
            FROM orders
            ORDER BY orders.updated_at;
            """;
    private static final String SELECT_ORDERS_BY_ORDER_STATUS = """
            SELECT orders.id, orders.created_at,
                   orders.updated_at, orders.customer_id,
                   orders.total_amount, orders.status
            FROM orders
            WHERE orders.status.id=?
            ORDER BY orders.updated_at;
            """;
    private static final String INSERT_INTO_ORDER = """
            INSERT INTO orders (id, created_at,
                                updated_at, customer_id,
                                total_amount, status)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    private static final String UPDATE_ORDER_BY_ID = """
            UPDATE orders
            SET updated_at=?, customer_id=?, total_amount=?, status=?
            WHERE orders.id=?;
            """;
    private static final String DELETE_ORDER_BY_ID = """
            DELETE FROM orders
            WHERE orders.id=?;
            """;

    @Override
    public Optional<Order> findById(UUID id) {
        var order = jdbcTemplate.query(
                SELECT_ORDER_BY_ID,
                orderMapper,
                id
        ).stream().findFirst();
        log.info("GET ORDER BY ID={}", id);
        return order;
    }

    @Override
    public List<Order> findAll() {
        var orders = jdbcTemplate.query(
                SELECT_ORDERS,
                orderMapper);
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
                    order.getOrderStatus().getStatus());
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
                    order.getOrderStatus().getStatus(),
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
                SELECT_ORDERS_BY_CUSTOMER_ID,
                orderMapper,
                customerId);
        log.info("GET {} ORDERS BY CUSTOMER ID={}", order.size(), customerId);
        return order;
    }

    @Override
    public List<Order> findAllByStatus(OrderStatus orderStatus) {
        var orders = jdbcTemplate.query(
                SELECT_ORDERS_BY_ORDER_STATUS,
                orderMapper,
                orderStatus.getStatus());
        log.info("GET {} ORDERS BY ORDER STATUS ID={}", orders.size(), orderStatus.getStatus());
        return orders;
    }
}
