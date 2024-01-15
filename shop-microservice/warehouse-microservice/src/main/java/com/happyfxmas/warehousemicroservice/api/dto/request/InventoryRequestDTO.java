package com.happyfxmas.warehousemicroservice.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class InventoryRequestDTO {
    @NotNull(message = "quantity must be not null!")
    @Min(value = 0, message = "quantity must be greater or equal to zero!")
    private Long quantity;

    @NotNull(message = "productId must be not null!")
    @NotBlank(message = "productId must be not empty!")
    private String productId;
}
