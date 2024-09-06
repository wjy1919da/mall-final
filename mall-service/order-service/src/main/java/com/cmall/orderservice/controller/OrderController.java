package com.cmall.orderservice.controller;

import com.cmall.orderservice.entity.Order;
import com.cmall.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable("userId") int userId){
        System.out.println("order service is called..");
        Mono<Order> order = orderService.createOrder(userId);
        return ResponseEntity.ok(order);
    }
}
