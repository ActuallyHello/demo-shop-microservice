package com.happyfxmas.ordermicroservice.api.controller;

import com.happyfxmas.ordermicroservice.service.OrderStatusService;
import com.happyfxmas.ordermicroservice.store.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/orderStatuses")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;
    @GetMapping
    public ResponseEntity<OrderStatus> getAllOrderStatuses() {
        return null;
    }
}
