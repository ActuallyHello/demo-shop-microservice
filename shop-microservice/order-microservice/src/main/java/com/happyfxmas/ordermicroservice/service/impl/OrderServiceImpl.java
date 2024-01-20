package com.happyfxmas.ordermicroservice.service.impl;

import com.happyfxmas.ordermicroservice.exception.response.OrderNotFoundException;
import com.happyfxmas.ordermicroservice.exception.response.OrderServerException;
import com.happyfxmas.ordermicroservice.exception.service.OrderCreationException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDeleteException;
import com.happyfxmas.ordermicroservice.service.OrderService;
import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.model.Order;
import com.happyfxmas.ordermicroservice.store.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order getById(UUID id) {
        log.info("GET ORDER WITH ID={}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("ORDER WITH ID={} DOES NOT EXIST!", id);
                    return new OrderNotFoundException("Order with id=%s was not found".formatted(id));
                });
    }

    @Override
    public List<Order> getAllByCustomerId(UUID customerId) {
        log.info("GETTING ALL CUSTOMER ORDERS WITH ID={}", customerId);
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Order> getAllByStatus(OrderStatus orderStatus) {
        log.info("GETTING ALL ORDERS WITH STATUS={}", orderStatus.getStatus());
        return orderRepository.findAllByStatus(orderStatus);
    }

    @Override
    public Order create(Order order, OrderStatus orderStatus) {
        log.info("SAVING ORDER WITH ID={}", order.getId());
        order.setOrderStatus(orderStatus);
        try {
            orderRepository.save(order);
            return order;
        } catch (OrderCreationException exception) {
            throw new OrderServerException(exception.getMessage(), exception);
        }
    }

    @Override
    public void update(Order oldOrder, Order newOrder) {
        log.info("UPDATE ORDER WITH ID={}", oldOrder.getId());
        try {
            orderRepository.update(oldOrder.getId(), newOrder);
        } catch (OrderCreationException exception) {
            throw new OrderServerException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Order order) {
        log.info("DELETING ORDER WITH ID={}", order.getId());
        try {
            orderRepository.delete(order);
        } catch (OrderDeleteException exception) {
            throw new OrderServerException(exception.getMessage(), exception);
        }
    }
}
