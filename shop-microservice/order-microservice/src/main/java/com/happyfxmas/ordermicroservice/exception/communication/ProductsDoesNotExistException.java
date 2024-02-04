package com.happyfxmas.ordermicroservice.exception.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductsDoesNotExistException extends RuntimeException {

    private List<UUID> unavailableProductIds = new ArrayList<>();

    public ProductsDoesNotExistException(String message) {
        super(message);
    }

    public ProductsDoesNotExistException(String message, List<UUID> unavailableProductIds) {
        super(message);
        this.unavailableProductIds = unavailableProductIds;
    }

    public ProductsDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public List<UUID> getUnavailableProductIds() {
        return new ArrayList<>(unavailableProductIds);
    }
}
