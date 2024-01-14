package com.happyfxmas.warehousemicroservice.store.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    PHONE("PHONE"), LAPTOP("LAPTOP"), TV("TV");

    private final String type;
}
