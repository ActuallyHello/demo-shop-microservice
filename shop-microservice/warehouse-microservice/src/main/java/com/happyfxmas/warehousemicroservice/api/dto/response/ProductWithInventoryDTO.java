package com.happyfxmas.warehousemicroservice.api.dto.response;

import com.happyfxmas.warehousemicroservice.api.dto.InventoryDTO;
import com.happyfxmas.warehousemicroservice.api.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ProductWithInventoryDTO extends ProductDTO {
    private InventoryDTO inventoryDTO;
}
