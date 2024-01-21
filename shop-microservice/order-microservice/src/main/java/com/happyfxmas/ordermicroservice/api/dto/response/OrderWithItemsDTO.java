package com.happyfxmas.ordermicroservice.api.dto.response;

import com.happyfxmas.ordermicroservice.api.dto.ItemDTO;
import com.happyfxmas.ordermicroservice.api.dto.OrderDTO;
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
public class OrderWithItemsDTO extends OrderDTO {
    private List<ItemDTO> items;
}
