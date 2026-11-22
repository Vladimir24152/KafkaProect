package org.neoflex.orders.controller;

import lombok.RequiredArgsConstructor;
import org.neoflex.orders.model.CreateOrderRequest;
import org.neoflex.orders.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public Map<String, String> createNewOrder(@RequestBody CreateOrderRequest request) {
        String orderId = service.createNewOrder(request);
        return Map.of("orderId", orderId, "status", "CREATED");
    }
}
