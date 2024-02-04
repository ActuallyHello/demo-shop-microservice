package com.happyfxmas.ordermicroservice.api.mapper;

import com.happyfxmas.ordermicroservice.api.dto.OrderDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.OrderRequestDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.OrderUpdateRequestDTO;
import com.happyfxmas.ordermicroservice.api.dto.response.OrderWithItemsDTO;
import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.model.Order;
import lombok.NonNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class OrderDTOMapper {
    public static Order makeModel(@NonNull OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        return Order.builder()
                .id(UUID.randomUUID())
                .customerId(UUID.fromString(orderRequestDTO.getCustomerId()))
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .totalAmount(BigDecimal.ZERO)
                .orderStatus(orderStatus)
                .build();
    }

    public static Order makeModel(@NonNull OrderUpdateRequestDTO orderUpdateRequestDTO, Order oldOrder) {
        if (orderUpdateRequestDTO.getCustomerId() == null && orderUpdateRequestDTO.getOrderStatus() == null) {
            throw new IllegalArgumentException("There are no fields to update in order!");
        }
        Order.OrderBuilder orderBuilder = Order.builder()
                .id(oldOrder.getId())
                .createdAt(oldOrder.getCreatedAt())
                .updatedAt(Timestamp.from(Instant.now()))
                .totalAmount(oldOrder.getTotalAmount());
        orderBuilder.customerId(orderUpdateRequestDTO.getCustomerId() != null
                ? UUID.fromString(orderUpdateRequestDTO.getCustomerId())
                : oldOrder.getCustomerId()
        );
        orderBuilder.orderStatus(orderUpdateRequestDTO.getOrderStatus() != null
                ? OrderStatus.valueOf(orderUpdateRequestDTO.getOrderStatus())
                : oldOrder.getOrderStatus()
        );
        return orderBuilder.build();
    }

    public static OrderDTO makeDTO(@NonNull Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .totalAmount(order.getTotalAmount())
                .build();
    }

    public static OrderWithItemsDTO makeDTO(@NonNull Order order, List<Item> items) {
        return OrderWithItemsDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .totalAmount(order.getTotalAmount())
                .items(items.stream()
                        .map(ItemDTOMapper::makeDTO)
                        .toList())
                .build();
    }
}
