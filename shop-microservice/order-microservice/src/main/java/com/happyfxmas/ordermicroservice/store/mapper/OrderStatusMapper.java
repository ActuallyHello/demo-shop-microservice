package com.happyfxmas.ordermicroservice.store.mapper;

import com.happyfxmas.ordermicroservice.store.model.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OrderStatusMapper implements RowMapper<OrderStatus> {
    @Override
    public OrderStatus mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return OrderStatus.builder()
                .id(resultSet.getObject("order_status.id", UUID.class))
                .code(resultSet.getString("order_status.code"))
                .build();
    }
}
