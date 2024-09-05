package com.cmall.paymentservice.controller;

import com.cmall.paymentservice.entity.Order;
import com.cmall.paymentservice.payload.OrderDto;
import com.cmall.paymentservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable("userId") int userId){
        Order order = orderService.createOrder(userId);
        return ResponseEntity.ok(order);
    }
}
