package com.happyfxmas.warehousemicroservice.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InventoryDTO {
    private UUID id;
    private Long quantity;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant updatedAt;
    private UUID productId;
}
