package com.happyfxmas.warehousemicroservice.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductRequestDTO {
    @NotNull(message = "title must be not null!")
    @NotBlank(message = "title must be not empty!")
    private String title;

    @NotNull(message = "description must be not null!")
    @NotBlank(message = "description must be not empty!")
    private String description;

    @NotNull(message = "type must be not null!")
    @NotBlank(message = "type must be not empty!")
    private String type;

    @NotNull(message = "supplierId must be not null!")
    @NotBlank(message = "supplierId must be not empty!")
    private String supplierId;
}
