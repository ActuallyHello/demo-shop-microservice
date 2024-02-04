package com.happyfxmas.ordermicroservice.service.facade;

import com.happyfxmas.ordermicroservice.service.ItemService;
import com.happyfxmas.ordermicroservice.service.OrderService;
import com.happyfxmas.ordermicroservice.store.model.Item;
import com.happyfxmas.ordermicroservice.store.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderFacade {
    private final OrderService orderService;
    private final ItemService itemService;

    public void updateOrderTotalAmountByItems(Order order) {
        var items = itemService.getAllByOrderId(order.getId());
        updateOrderTotalAmount(order, items);
    }

    public void updateOrderTotalAmountByItems(Order order, List<Item> items) {
        updateOrderTotalAmount(order, items);
    }

    private BigDecimal calculateNewTotalAmount(List<Item> items) {
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void updateOrderTotalAmount(Order order, List<Item> items) {
        log.info("PROCESSING UPDATE TOTAL AMOUNT FOR ORDER WITH ID={}!", order.getId());
        var newTotalAmount = calculateNewTotalAmount(items);
        order.setTotalAmount(newTotalAmount);
        orderService.update(order);
    }
}
