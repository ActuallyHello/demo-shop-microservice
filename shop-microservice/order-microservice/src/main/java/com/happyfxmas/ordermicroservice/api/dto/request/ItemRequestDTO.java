package com.happyfxmas.ordermicroservice.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@Getter
public class ItemRequestDTO {
    @NotNull(message = "productId must be not null")
    @NotBlank(message = "productId must be not blank")
    @Length(max = 36, message = "productId cannot be more than 36 symbols")
    private String productId;

    @NotNull(message = "customerId must be not null")
    @Min(value = 0, message = "price cannot be less than zero!")
    private BigDecimal price;

    @NotNull(message = "customerId must be not null")
    @Min(value = 0, message = "quantity cannot be less than zero!")
    private BigInteger quantity;

    @NotNull(message = "itemStatus must be not null")
    @NotBlank(message = "itemStatus must be not blank")
    private String itemStatus;
}
