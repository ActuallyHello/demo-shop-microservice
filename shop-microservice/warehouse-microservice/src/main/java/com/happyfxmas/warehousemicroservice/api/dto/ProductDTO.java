package com.happyfxmas.warehousemicroservice.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.happyfxmas.warehousemicroservice.store.model.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ProductDTO {
    private UUID id;
    private String title;
    private String description;
    private ProductType type;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant updatedAt;
    private UUID supplierId;
}
