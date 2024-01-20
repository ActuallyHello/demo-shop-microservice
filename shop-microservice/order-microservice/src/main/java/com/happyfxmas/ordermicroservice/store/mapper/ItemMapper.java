package com.happyfxmas.ordermicroservice.store.mapper;

import com.happyfxmas.ordermicroservice.store.enums.ItemStatus;
import com.happyfxmas.ordermicroservice.store.model.Item;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ItemMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Item.builder()
                .id(resultSet.getObject("item.id", UUID.class))
                .createdAt(resultSet.getTimestamp("item.created_at"))
                .updatedAt(resultSet.getTimestamp("item.updated_at"))
                .price(resultSet.getBigDecimal("item.price"))
                .quantity(resultSet.getObject("item.quantity", BigInteger.class))
                .productId(resultSet.getObject("item.product_id", UUID.class))
                .itemStatus(ItemStatus.valueOf(resultSet.getString("item.status")))
                .build();
    }
}
