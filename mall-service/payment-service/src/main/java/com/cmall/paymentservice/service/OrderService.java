package com.cmall.paymentservice.service;
import com.cmall.paymentservice.dao.OrderRepository;
import com.cmall.paymentservice.entity.Order;
import com.cmall.paymentservice.payload.OrderDto;

public interface OrderService {
    public Order createOrder(int userId);
//    public Order getAllOrders(int userId);

}
