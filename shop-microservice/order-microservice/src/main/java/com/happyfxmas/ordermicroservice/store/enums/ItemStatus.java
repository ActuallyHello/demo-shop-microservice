package com.happyfxmas.ordermicroservice.store.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public enum ItemStatus {
    IN_STOCK("IN_STOCK"), OUT_OF_STOCK("OUT_OF_STOCK");

    private final String status;
}
