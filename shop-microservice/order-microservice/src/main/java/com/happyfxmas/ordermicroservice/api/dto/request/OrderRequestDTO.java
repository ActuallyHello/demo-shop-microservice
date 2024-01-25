package com.happyfxmas.ordermicroservice.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequestDTO {
    @NotNull(message = "customerId must be not null")
    @NotBlank(message = "customerId must be not blank")
    @Length(max = 36, message = "customerId cannot be more than 36 symbols")
    private String customerId;
}
