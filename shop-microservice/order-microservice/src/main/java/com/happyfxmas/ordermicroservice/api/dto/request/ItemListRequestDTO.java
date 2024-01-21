package com.happyfxmas.ordermicroservice.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@Getter
public class ItemListRequestDTO {
    @NotNull(message = "orderId must be not null")
    @NotBlank(message = "orderId must be not blank")
    @Length(max = 36, message = "orderId cannot be more than 36 symbols")
    private String orderId;

    @Valid
    private List<ItemRequestDTO> items;
}
