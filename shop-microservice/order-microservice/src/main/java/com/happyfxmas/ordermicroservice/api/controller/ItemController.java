package com.happyfxmas.ordermicroservice.api.controller;

import com.happyfxmas.ordermicroservice.api.dto.ItemDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.ItemListRequestDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.ItemUpdateRequestDTO;
import com.happyfxmas.ordermicroservice.api.mapper.ItemDTOMapper;
import com.happyfxmas.ordermicroservice.service.ItemService;
import com.happyfxmas.ordermicroservice.service.OrderService;
import com.happyfxmas.ordermicroservice.service.communication.WarehouseCommunication;
import com.happyfxmas.ordermicroservice.service.facade.OrderFacade;
import com.happyfxmas.ordermicroservice.store.enums.ItemStatus;
import com.happyfxmas.ordermicroservice.store.model.Item;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/items")
public class ItemController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final OrderFacade orderFacade;
    private final WarehouseCommunication warehouseCommunication;

    private static final String BY_ID = "/{id}";
    private static final String ALL_STATUSES = "/statuses";

    @GetMapping(BY_ID)
    public ResponseEntity<ItemDTO> getItemById(@PathVariable UUID id) {
        var item = itemService.getById(id);
        return ResponseEntity.ok(ItemDTOMapper.makeDTO(item));
    }

    @GetMapping(ALL_STATUSES)
    public ResponseEntity<List<String>> getItemStatuses() {
        var statuses = Arrays.stream(ItemStatus.values())
                .map(ItemStatus::getStatus)
                .toList();
        return ResponseEntity.ok(statuses);
    }

    @PostMapping
    public ResponseEntity<?> createItemsForOrder(@RequestBody @Valid ItemListRequestDTO itemListRequestDTO) {
        var order = orderService.getById(UUID.fromString(itemListRequestDTO.getOrderId()));
        var items = itemListRequestDTO.getItems().stream()
                    .map(item -> ItemDTOMapper.makeModel(item, order))
                    .toList();
        warehouseCommunication.checkForUnavailableProductsInItems(items);
        items = itemService.createAll(items);
        orderFacade.updateOrderTotalAmountByItems(order, items);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("ID", items.stream()
                        .map(Item::getId)
                        .toList()));
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<?> updateItemById(@PathVariable UUID id,
                                            @RequestBody @Valid ItemUpdateRequestDTO itemUpdateRequestDTO) {
        var oldItem = itemService.getById(id);
        var newItem = ItemDTOMapper.makeModel(itemUpdateRequestDTO, oldItem);
        itemService.update(newItem);
        orderFacade.updateOrderTotalAmountByItems(newItem.getOrder());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<?> deleteItemById(@PathVariable UUID id) {
        var item = itemService.getById(id);
        itemService.delete(item);
        orderFacade.updateOrderTotalAmountByItems(item.getOrder());
        return ResponseEntity.noContent().build();
    }
}
