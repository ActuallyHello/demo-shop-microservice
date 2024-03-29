package com.happyfxmas.warehousemicroservice.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductUpdateRequestDTO {
    @NotBlank(message = "title must be not empty!")
    private String title;

    @NotBlank(message = "description must be not empty!")
    private String description;

    @NotBlank(message = "type must be not empty!")
    private String type;
}
