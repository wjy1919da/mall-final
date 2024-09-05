package com.cmall.paymentservice.service;
import com.cmall.paymentservice.dao.OrderRepository;
import com.cmall.paymentservice.payload.OrderDto;

public interface OrderService {
    public OrderDto createOrder(int userId);

}
