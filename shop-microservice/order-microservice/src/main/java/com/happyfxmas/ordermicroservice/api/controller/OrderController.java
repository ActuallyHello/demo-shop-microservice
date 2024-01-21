package com.happyfxmas.ordermicroservice.api.controller;

import com.happyfxmas.ordermicroservice.api.dto.OrderDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.OrderRequestDTO;
import com.happyfxmas.ordermicroservice.api.dto.request.OrderUpdateRequestDTO;
import com.happyfxmas.ordermicroservice.api.dto.response.OrderWithItemsDTO;
import com.happyfxmas.ordermicroservice.api.mapper.OrderDTOMapper;
import com.happyfxmas.ordermicroservice.api.mapper.OrderWithItemsDTOMapper;
import com.happyfxmas.ordermicroservice.exception.response.OrderNotFoundException;
import com.happyfxmas.ordermicroservice.exception.response.OrderServerException;
import com.happyfxmas.ordermicroservice.exception.response.OrderValidationException;
import com.happyfxmas.ordermicroservice.exception.service.OrderCreationException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDeleteException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDoesNotExistException;
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
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@RequestParam(value = "status") List<String> statuses) {
        List<OrderStatus> orderStatuses;
        try {
            orderStatuses = statuses.stream()
                    .map(OrderStatus::valueOf)
                    .toList();
        } catch (IllegalArgumentException exception) {
            throw new OrderValidationException(exception.getMessage(), exception);
        }
        var orders = orderService.getAllByStatusIn(orderStatuses);
        var orderDTOs = orders.stream()
                .map(OrderDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID id) {
        try {
            var order = orderService.getById(id);
            return ResponseEntity.ok(OrderDTOMapper.makeDTO(order));
        } catch (OrderDoesNotExistException exception) {
            throw new OrderNotFoundException(exception.getMessage(), exception);
        }
    }

    @GetMapping(BY_ID_WITH_ITEMS)
    public ResponseEntity<OrderWithItemsDTO> getOrderWithItems(@PathVariable UUID id) {
        Order order;
        try {
            order = orderService.getById(id);
        } catch (OrderDoesNotExistException exception) {
            throw new OrderNotFoundException(exception.getMessage(), exception);
        }
        List<Item> items = itemService.getAllByOrderId(order.getId());
        return ResponseEntity.ok(OrderWithItemsDTOMapper.makeDTO(order, items));
    }

    @GetMapping(ALL_STATUSES)
    public ResponseEntity<List<String>> getOrderStatuses() {
        List<String> statuses = Arrays.stream(OrderStatus.values())
                .map(OrderStatus::getStatus)
                .toList();
        return ResponseEntity.ok(statuses);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        Order order;
        try {
            order = OrderDTOMapper.makeModel(orderRequestDTO, OrderStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            throw new OrderValidationException(exception.getMessage(), exception);
        }
        try {
            order = orderService.create(order);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(OrderDTOMapper.makeDTO(order));
        } catch (OrderCreationException exception) {
            throw new OrderServerException(exception.getMessage(), exception);
        }
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<?> updateOrderById(@PathVariable UUID id,
                                             @RequestBody @Valid OrderUpdateRequestDTO orderUpdateRequestDTO) {
        Order oldOrder;
        try {
            oldOrder = orderService.getById(id);
        } catch (OrderDoesNotExistException exception) {
            throw new OrderNotFoundException(exception.getMessage(), exception);
        }
        Order newOrder;
        try {
            newOrder = OrderDTOMapper.makeModel(orderUpdateRequestDTO, oldOrder);
        } catch (IllegalArgumentException exception) {
            throw new OrderValidationException(exception.getMessage(), exception);
        }
        try {
            orderService.update(newOrder);
            return ResponseEntity.noContent().build();
        } catch (OrderCreationException exception) {
            throw new OrderServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<?> deleteOrderById(@PathVariable UUID id) {
        Order order;
        try {
            order = orderService.getById(id);
        } catch (OrderDoesNotExistException exception) {
            throw new OrderNotFoundException(exception.getMessage(), exception);
        }
        try {
            orderService.delete(order);
            return ResponseEntity.noContent().build();
        } catch (OrderDeleteException exception) {
            throw new OrderServerException(exception.getMessage(), exception);
        }
    }
}
