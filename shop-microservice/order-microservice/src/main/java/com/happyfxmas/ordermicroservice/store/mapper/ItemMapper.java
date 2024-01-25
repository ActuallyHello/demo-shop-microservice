package com.happyfxmas.ordermicroservice.store.mapper;

import com.happyfxmas.ordermicroservice.store.enums.ItemStatus;
import com.happyfxmas.ordermicroservice.store.model.Item;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ItemMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Item.builder()
                .id(resultSet.getObject("id", UUID.class))
                .createdAt(resultSet.getTimestamp("created_at"))
                .updatedAt(resultSet.getTimestamp("updated_at"))
                .price(resultSet.getBigDecimal("price"))
                .quantity(resultSet.getObject("quantity", BigInteger.class))
                .productId(resultSet.getObject("product_id", UUID.class))
                .itemStatus(ItemStatus.valueOf(resultSet.getString("status")))
                .build();
    }
}
