package com.happyfxmas.ordermicroservice.api.dto;

import com.happyfxmas.ordermicroservice.store.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ItemDTO {
    private UUID id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private UUID productId;
    private BigDecimal price;
    private BigInteger quantity;
    private ItemStatus itemStatus;
}
