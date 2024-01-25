package com.happyfxmas.ordermicroservice.api.mapper;

import com.happyfxmas.ordermicroservice.api.dto.ItemDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.ItemRequestDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.ItemUpdateRequestDTO;
import com.happyfxmas.ordermicroservice.store.enums.ItemStatus;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.model.Order;
import lombok.NonNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class ItemDTOMapper {
    public static ItemDTO makeDTO(@NonNull Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .itemStatus(item.getItemStatus())
                .price(item.getPrice())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .build();
    }

    public static Item makeModel(@NonNull ItemRequestDTO itemRequestDTO, Order order) {
        return Item.builder()
                .id(UUID.randomUUID())
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .itemStatus(ItemStatus.valueOf(itemRequestDTO.getItemStatus()))
                .price(itemRequestDTO.getPrice())
                .quantity(itemRequestDTO.getQuantity())
                .productId(UUID.fromString(itemRequestDTO.getProductId()))
                .order(order)
                .build();
    }

    public static Item makeModel(@NonNull ItemUpdateRequestDTO itemUpdateRequestDTO, Item oldItem) {
        if (itemUpdateRequestDTO.getItemStatus() == null && itemUpdateRequestDTO.getPrice() == null &&
                itemUpdateRequestDTO.getQuantity() == null) {
            throw new IllegalArgumentException("There are no fields to update in item!");
        }
        Item.ItemBuilder itemBuilder = Item.builder()
                .id(oldItem.getId())
                .createdAt(oldItem.getCreatedAt())
                .updatedAt(Timestamp.from(Instant.now()))
                .productId(oldItem.getProductId())
                .order(oldItem.getOrder());
        itemBuilder.itemStatus(itemUpdateRequestDTO.getItemStatus() != null
                ? ItemStatus.valueOf(itemUpdateRequestDTO.getItemStatus())
                : oldItem.getItemStatus());
        itemBuilder.price(itemUpdateRequestDTO.getPrice() != null
                ? itemUpdateRequestDTO.getPrice()
                : oldItem.getPrice());
        itemBuilder.quantity(itemUpdateRequestDTO.getQuantity() != null
                ? itemUpdateRequestDTO.getQuantity()
                : oldItem.getQuantity());
        return itemBuilder.build();
    }
}
