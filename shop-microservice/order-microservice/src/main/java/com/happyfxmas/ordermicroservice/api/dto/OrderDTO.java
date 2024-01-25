package com.happyfxmas.ordermicroservice.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class OrderDTO {
    private UUID id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Timestamp createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Timestamp updatedAt;
    private UUID customerId;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
}
