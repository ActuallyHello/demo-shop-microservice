package com.happyfxmas.warehousemicroservice.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SupplierRequestDTO {
    @NotNull(message = "companyName must be not null!")
    @NotBlank(message = "companyName must be not empty!")
    private String companyName;

    @NotNull(message = "email must be not null!")
    @Email(message = "email must be valid email!")
    private String email;

    @NotNull(message = "phone must be not null!")
    @NotBlank(message = "phone must be not empty!")
    private String phone;
}
