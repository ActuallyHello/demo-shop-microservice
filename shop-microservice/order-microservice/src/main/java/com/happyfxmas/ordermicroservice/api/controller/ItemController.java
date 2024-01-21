package com.happyfxmas.ordermicroservice.api.controller;

import com.happyfxmas.ordermicroservice.api.dto.ItemDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.ItemListRequestDTO;
import com.happyfxmas.ordermicroservice.api.mapper.ItemDTOMapper;
import com.happyfxmas.ordermicroservice.exception.response.ItemServerException;
import com.happyfxmas.ordermicroservice.exception.response.ItemValidationException;
import com.happyfxmas.ordermicroservice.exception.service.ItemCreationException;
import com.happyfxmas.ordermicroservice.exception.service.ItemDoesNotExistException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDoesNotExistException;
import com.happyfxmas.ordermicroservice.service.ItemService;
import com.happyfxmas.ordermicroservice.service.OrderService;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.model.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/items")
public class ItemController {

    private final ItemService itemService;
    private final OrderService orderService;
    private static final String BY_ID = "/{id}";

    @GetMapping(BY_ID)
    public ResponseEntity<ItemDTO> getItemById(@PathVariable UUID id) {
        try {
            var item = itemService.getById(id);
            return ResponseEntity.ok(ItemDTOMapper.makeDTO(item));
        } catch (ItemDoesNotExistException exception) {
            throw new ItemDoesNotExistException(exception.getMessage(), exception);
        }
    }

    @PostMapping
    public ResponseEntity<?> createItemsForOrder(@RequestBody @Valid ItemListRequestDTO itemListRequestDTO) {
        UUID orderId;
        try {
            orderId = UUID.fromString(itemListRequestDTO.getOrderId());
        } catch (IllegalArgumentException exception) {
            throw new ItemValidationException(exception.getMessage(), exception);
        }
        Order order;
        try {
            order = orderService.getById(orderId);
        } catch (OrderDoesNotExistException exception) {
            throw new ItemServerException(exception.getMessage(), exception);
        }
        List<Item> items = itemListRequestDTO.getItems().stream()
                .map(item -> ItemDTOMapper.makeModel(item, order))
                .toList();
        try {
            items = itemService.createAll(items);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(items.stream()
                            .map(ItemDTOMapper::makeDTO)
                            .toList());
        } catch (ItemCreationException exception) {
            throw new ItemCreationException(exception.getMessage(), exception);
        }
    }
}
