package com.happyfxmas.ordermicroservice.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemUpdateRequestDTO {
    @NotNull(message = "price must be not null")
    @Min(value = 0, message = "price cannot be less than zero!")
    private BigDecimal price;

    @NotNull(message = "quantity must be not null")
    @Min(value = 0, message = "quantity cannot be less than zero!")
    private BigInteger quantity;

    @NotNull(message = "itemStatus must be not null")
    @NotBlank(message = "itemStatus must be not blank")
    private String itemStatus;
}
