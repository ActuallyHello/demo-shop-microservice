package com.happyfxmas.ordermicroservice.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    @Id
    private UUID id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private UUID customerId;
    private BigDecimal totalAmount;

    private OrderStatus orderStatus;
}
