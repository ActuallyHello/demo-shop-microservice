package com.happyfxmas.ordermicroservice.store.mapper;

import com.happyfxmas.ordermicroservice.store.model.ItemStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemStatusMapper implements RowMapper<ItemStatus> {
    @Override
    public ItemStatus mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return ItemStatus.builder()
                .id(resultSet.getObject("item_status.id", java.util.UUID.class))
                .code(resultSet.getString("item_status.code"))
                .build();
    }
}
