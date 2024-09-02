package com.cmall.paymentservice.service.Impl;

import com.cmall.common.payload.AccountDetailResponse;
import com.cmall.paymentservice.dao.OrderRepository;
import com.cmall.paymentservice.entity.Order;
import com.cmall.paymentservice.feign.UserClient;
import com.cmall.paymentservice.payload.OrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl {
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    ModelMapper modelMapper;
//
//    @Autowired
//    UserClient userClient;
//
//    @Override
//    public OrderDto createOrder(OrderDto orderDto){
//        Order order = new Order();
//        AccountDetailResponse userInfo = userClient.getUserDetails(orderDto.getUserId());
//
//    }
}
