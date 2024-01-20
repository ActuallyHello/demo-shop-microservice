package com.happyfxmas.ordermicroservice.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderRequestDTO {
    @NotNull(message = "customerId must be not null")
    @NotBlank(message = "customerId must be not blank")
    private String customerId;
}
