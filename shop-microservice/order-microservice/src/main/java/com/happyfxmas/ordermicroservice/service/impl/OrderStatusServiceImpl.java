package com.happyfxmas.ordermicroservice.service.impl;

import com.happyfxmas.ordermicroservice.service.OrderStatusService;
import com.happyfxmas.ordermicroservice.store.model.OrderStatus;
import com.happyfxmas.ordermicroservice.store.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Override
    public OrderStatus getById(Integer id) {
        return null;
    }

    @Override
    public List<OrderStatus> getAll() {
        return null;
    }

    @Override
    public void create(String code) {

    }

    @Override
    public void update(Integer id, OrderStatus orderStatus) {

    }

    @Override
    public void delete(OrderStatus orderStatus) {

    }
}
