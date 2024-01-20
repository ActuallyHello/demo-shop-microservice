package com.happyfxmas.ordermicroservice.store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public enum OrderStatus {
    CREATED("CREATED"), CANCELLED("CANCELLED"), DELIVERED("DELIVERED");

    private final String status;
}
