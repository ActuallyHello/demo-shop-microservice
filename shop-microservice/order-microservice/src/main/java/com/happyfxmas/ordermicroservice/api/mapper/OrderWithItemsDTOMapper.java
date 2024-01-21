package com.happyfxmas.ordermicroservice.api.mapper;

import com.happyfxmas.ordermicroservice.api.dto.response.OrderWithItemsDTO;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.model.Order;
import lombok.NonNull;

import java.util.List;

public class OrderWithItemsDTOMapper {
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
