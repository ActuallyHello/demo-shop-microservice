package com.happyfxmas.ordermicroservice.store.repository.impl;

import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.mapper.OrderMapper;
import com.happyfxmas.ordermicroservice.store.model.Order;
import com.happyfxmas.ordermicroservice.store.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final OrderMapper orderMapper;

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
    private static final String SELECT_ORDERS_BY_STATUSES = """
            SELECT orders.id, orders.created_at,
                   orders.updated_at, orders.customer_id,
                   orders.total_amount, orders.status
            FROM orders
            WHERE orders.status IN (:statuses)
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
        return jdbcTemplate.query(SELECT_ORDER_BY_ID, orderMapper, id).stream().findFirst();
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(SELECT_ORDERS, orderMapper);
    }

    @Override
    public void save(Order order) {
        System.out.println(order);
        jdbcTemplate.update(
                INSERT_INTO_ORDER,
                order.getId(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getOrderStatus().getStatus());
    }

    @Override
    public void update(Order order) {
        jdbcTemplate.update(
                UPDATE_ORDER_BY_ID,
                order.getUpdatedAt(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getOrderStatus().getStatus(),
                order.getId());
    }

    @Override
    public void delete(Order order) {
        jdbcTemplate.update(DELETE_ORDER_BY_ID, order.getId());
    }

    @Override
    public List<Order> findAllByCustomerId(UUID customerId) {
        return jdbcTemplate.query(SELECT_ORDERS_BY_CUSTOMER_ID, orderMapper, customerId);
    }

    @Override
    public List<Order> findAllByStatusIn(List<OrderStatus> orderStatuses) {
        Map<String, List<String>> statuses = Collections.singletonMap(
                "statuses",
                orderStatuses.stream()
                        .map(OrderStatus::getStatus)
                        .toList()
        );
        return namedParameterJdbcTemplate.query(SELECT_ORDERS_BY_STATUSES, statuses, orderMapper);
    }
}
