package com.happyfxmas.ordermicroservice.store.mapper;

import com.happyfxmas.ordermicroservice.store.model.Order;
import com.happyfxmas.ordermicroservice.store.model.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OrderWithStatusMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Order.builder()
                .id(resultSet.getObject("orders.id", UUID.class))
                .createdAt(resultSet.getTimestamp("orders.created_at"))
                .updatedAt(resultSet.getTimestamp("orders.updated_at"))
                .customerId(resultSet.getObject("orders.customer_id", UUID.class))
                .totalAmount(resultSet.getBigDecimal("orders.total_amount"))
                .orderStatus(
                        OrderStatus.builder()
                                .id(resultSet.getObject("order_status.id", UUID.class))
                                .code(resultSet.getString("order_status.code"))
                                .build()
                )
                .build();
    }
}
