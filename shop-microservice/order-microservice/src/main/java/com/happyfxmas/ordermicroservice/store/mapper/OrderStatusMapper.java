package com.happyfxmas.ordermicroservice.store.mapper;

import com.happyfxmas.ordermicroservice.store.model.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusMapper implements RowMapper<OrderStatus> {
    @Override
    public OrderStatus mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return OrderStatus.builder()
                .id(resultSet.getInt("order_status.id"))
                .code(resultSet.getString("order_status.code"))
                .build();
    }
}
