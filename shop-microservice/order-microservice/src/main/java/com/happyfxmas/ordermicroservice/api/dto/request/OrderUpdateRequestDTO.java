package com.happyfxmas.ordermicroservice.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class OrderUpdateRequestDTO {
    @NotBlank(message = "customerId must be not blank")
    @Length(max = 36, message = "customerId cannot be more than 36 symbols")
    private String customerId;

    @Min(value = 0, message = "totalAmount cannot be less than zero!")
    private BigDecimal totalAmount;

    @NotBlank(message = "orderStatus must be not blank")
    private String orderStatus;
}
