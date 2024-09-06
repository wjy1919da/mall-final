package com.cmall.orderservice.service;

import com.cmall.orderservice.entity.Order;
import reactor.core.publisher.Mono;

public interface OrderService {
    public Mono<Order> createOrder(int userId);
}
