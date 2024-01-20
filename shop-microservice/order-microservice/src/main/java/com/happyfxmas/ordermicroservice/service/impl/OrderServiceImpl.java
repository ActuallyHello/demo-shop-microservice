package com.happyfxmas.ordermicroservice.service.impl;

import com.happyfxmas.ordermicroservice.api.dto.request.OrderRequestDTO;
import com.happyfxmas.ordermicroservice.service.OrderService;
import com.happyfxmas.ordermicroservice.store.model.Order;
import com.happyfxmas.ordermicroservice.store.model.OrderStatus;
import com.happyfxmas.ordermicroservice.store.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order getById(UUID id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order create(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        Order order = Order.builder()
                .customerId(UUID.fromString(orderRequestDTO.getCustomerId()))
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .totalAmount(BigDecimal.ZERO)
                .orderStatus(orderStatus)
                .build();
        orderRepository.save(order);
        return order;
    }

    @Override
    public void update(Order oldOrder, Order newOrder) {

    }

    @Override
    public void delete(Order order) {

    }
}
