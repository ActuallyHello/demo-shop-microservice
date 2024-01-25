package com.happyfxmas.ordermicroservice.service.impl;

import com.happyfxmas.ordermicroservice.exception.service.OrderCreationException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDeleteException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDoesNotExistException;
import com.happyfxmas.ordermicroservice.service.OrderService;
import com.happyfxmas.ordermicroservice.store.enums.OrderStatus;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.model.Order;
import com.happyfxmas.ordermicroservice.store.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private static final String DATABASE_TAG = "[DATABASE]";

    @Override
    public Order getById(UUID id) {
        log.info("GET ORDER WITH ID={}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("ORDER WITH ID={} DOES NOT EXIST!", id);
                    return new OrderDoesNotExistException("Order with id=%s was not found".formatted(id));
                });
    }

    @Override
    public List<Order> getAllByCustomerId(UUID customerId) {
        log.info("GETTING ALL CUSTOMER ORDERS WITH ID={}", customerId);
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Order> getAllByStatusIn(List<OrderStatus> orderStatuses) {
        log.info("GETTING ALL ORDERS WITH STATUSES={}", orderStatuses);
        return orderRepository.findAllByStatusIn(orderStatuses);
    }

    @Override
    @Transactional
    public Order create(Order order) {
        log.info("TRY TO SAVE");
        try {
            orderRepository.save(order);
            log.info("CREATED ORDER WITH ID={}", order.getId());
            return order;
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN CREATING ORDER: {}", exception.getMessage());
            throw new OrderCreationException("Error when creating order! " + DATABASE_TAG, exception);
        }
    }

    @Override
    @Transactional
    public void update(Order newOrder) {
        try {
            orderRepository.update(newOrder);
            log.info("UPDATED ORDER WITH ID={}", newOrder.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN UPDATING ORDER: {}", exception.getMessage());
            throw new OrderCreationException("Error when updating order! " + DATABASE_TAG, exception);
        }
    }

    @Override
    @Transactional
    public void delete(Order order) {
        try {
            orderRepository.delete(order);
            log.info("DELETED ORDER WITH ID={}", order.getId());
        } catch (DataAccessException exception) {
            log.error("ERROR WHEN DELETING ORDER: {}", exception.getMessage());
            throw new OrderDeleteException("Error when deleting order! " + DATABASE_TAG, exception);
        }
    }

    @Override
    @Transactional
    public void updateTotalAmountByItems(Order order, List<Item> items) {
        BigDecimal totalAmount = items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        order.setUpdatedAt(Timestamp.from(Instant.now()));
        update(order);
    }
}
