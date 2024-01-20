package com.happyfxmas.ordermicroservice.store.mapper;

import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.model.ItemStatus;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ItemWithStatusMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Item.builder()
                .id(resultSet.getObject("item.id", UUID.class))
                .createdAt(resultSet.getTimestamp("item.created_at"))
                .updatedAt(resultSet.getTimestamp("item.updated_at"))
                .price(resultSet.getBigDecimal("item.price"))
                .quantity(resultSet.getObject("item.quantity", BigInteger.class))
                .productId(resultSet.getObject("item.product_id", UUID.class))
                .itemStatus(
                        ItemStatus.builder()
                                .id(resultSet.getInt("item_status.id"))
                                .code(resultSet.getString("item_status.code"))
                                .build()
                )
                .build();
    }
}
