package com.spring.ecom_app.controller;

import com.spring.ecom_app.model.dto.OrderResponse;
import com.spring.ecom_app.model.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-ID") String userId) {
        return this.orderService.createOrder(userId).map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED)).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
