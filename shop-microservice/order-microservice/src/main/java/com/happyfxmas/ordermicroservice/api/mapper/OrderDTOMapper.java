package com.happyfxmas.ordermicroservice.api.mapper;

import com.happyfxmas.ordermicroservice.api.dto.request.OrderRequestDTO;
import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.model.Order;
import lombok.NonNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class OrderDTOMapper {
    public static Order makeModel(@NonNull OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        return Order.builder()
                .customerId(UUID.fromString(orderRequestDTO.getCustomerId()))
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .totalAmount(BigDecimal.ZERO)
                .orderStatus(orderStatus)
                .build();
    }
}
