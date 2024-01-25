package com.happyfxmas.ordermicroservice.api.controller;

import com.happyfxmas.ordermicroservice.api.dto.OrderDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.OrderRequestDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.OrderUpdateRequestDTO;
import com.happyfxmas.ordermicroservice.api.dto.response.OrderWithItemsDTO;
import com.happyfxmas.ordermicroservice.api.mapper.OrderDTOMapper;
import com.happyfxmas.ordermicroservice.service.ItemService;
import com.happyfxmas.ordermicroservice.service.OrderService;
import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.model.Order;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/orders")
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private static final String BY_ID = "/{id}";
    private static final String BY_ID_WITH_ITEMS = "/{id}/items";
    private static final String ALL_STATUSES = "/statuses";

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(
            @RequestParam(value = "status", required = false) List<String> statuses) {
        var orderStatuses = statuses != null
                ? statuses.stream().map(OrderStatus::valueOf).toList()
                : Arrays.asList(OrderStatus.values());
        var orders = orderService.getAllByStatusIn(orderStatuses);
        var orderDTOs = orders.stream()
                .map(OrderDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID id) {
        var order = orderService.getById(id);
        return ResponseEntity.ok(OrderDTOMapper.makeDTO(order));
    }

    @GetMapping(BY_ID_WITH_ITEMS)
    public ResponseEntity<OrderWithItemsDTO> getOrderWithItems(@PathVariable UUID id) {
        Order order = orderService.getById(id);
        List<Item> items = itemService.getAllByOrderId(order.getId());
        return ResponseEntity.ok(OrderDTOMapper.makeDTO(order, items));
    }

    @GetMapping(ALL_STATUSES)
    public ResponseEntity<List<String>> getOrderStatuses() {
        var statuses = Arrays.stream(OrderStatus.values())
                .map(OrderStatus::getStatus)
                .toList();
        return ResponseEntity.ok(statuses);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        var order = OrderDTOMapper.makeModel(orderRequestDTO, OrderStatus.CREATED);
        order = orderService.create(order);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("ID", order.getId().toString()));
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<?> updateOrderById(@PathVariable UUID id,
                                             @RequestBody @Valid OrderUpdateRequestDTO orderUpdateRequestDTO) {
        var oldOrder = orderService.getById(id);
        var newOrder = OrderDTOMapper.makeModel(orderUpdateRequestDTO, oldOrder);
        orderService.update(newOrder);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<?> deleteOrderById(@PathVariable UUID id) {
        var order = orderService.getById(id);
        orderService.delete(order);
        return ResponseEntity.noContent().build();
    }
}
