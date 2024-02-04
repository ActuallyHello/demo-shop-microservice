package com.happyfxmas.ordermicroservice.store.model;

import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Order {
    @Id
    private UUID id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private UUID customerId;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
}
