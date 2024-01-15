package com.happyfxmas.warehousemicroservice.api.dto.response;

import com.happyfxmas.warehousemicroservice.api.dto.ProductDTO;
import com.happyfxmas.warehousemicroservice.api.dto.SupplierDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class SupplierWithProductDTO extends SupplierDTO {
    private List<ProductDTO> productDTOs;
}
