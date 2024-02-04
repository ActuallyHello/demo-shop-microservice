package com.happyfxmas.ordermicroservice.service.communication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private UUID id;
    private String title;
    private String description;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate updatedAt;
    private UUID supplierId;
}
