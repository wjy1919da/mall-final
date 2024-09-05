package com.cmall.paymentservice.service.Impl;

import com.cmall.common.payload.AccountDetailResponse;
import com.cmall.paymentservice.dao.OrderRepository;
import com.cmall.paymentservice.entity.Order;
import com.cmall.paymentservice.feign.CartClient;
import com.cmall.paymentservice.feign.UserClient;
import com.cmall.paymentservice.payload.CartDto;
import com.cmall.paymentservice.payload.CartItemDto;
import com.cmall.paymentservice.payload.OrderDto;
import com.cmall.paymentservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserClient userClient;

    @Autowired
    private CartClient cartClient;

    @Override
    public OrderDto createOrder(int userId){
        AccountDetailResponse userInfo = userClient.getUserDetails(userId);
        CartDto cartInfo = cartClient.getCartByUserId(userId);
        OrderDto orderDto = new OrderDto();
        modelMapper.map(userInfo, orderDto);
        modelMapper.map(cartInfo, orderDto);
        Order order = new Order();
        modelMapper.map(orderDto, order);
        order.setItems(cartInfo.getItems().stream()
                .collect(Collectors.toMap(CartItemDto::getItemId, CartItemDto::getQuantity)));


        return orderDto;
    }
}
