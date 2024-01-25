package com.happyfxmas.ordermicroservice.store.mapper;

import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.model.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Order.builder()
                .id(resultSet.getObject("id", UUID.class))
                .createdAt(resultSet.getTimestamp("created_at"))
                .updatedAt(resultSet.getTimestamp("updated_at"))
                .customerId(resultSet.getObject("customer_id", UUID.class))
                .totalAmount(resultSet.getBigDecimal("total_amount"))
                .orderStatus(OrderStatus.valueOf(resultSet.getString("status")))
                .build();
    }
}
