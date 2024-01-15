package com.happyfxmas.warehousemicroservice.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SupplierUpdateRequestDTO {
    @NotBlank(message = "companyName must be not empty!")
    private String companyName;

    @Email(message = "email must be valid email!")
    private String email;

    @NotBlank(message = "phone must be not empty!")
    private String phone;
}
