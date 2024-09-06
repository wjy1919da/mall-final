package com.cmall.orderservice.payload;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID orderId;
    private Integer userId;
    private Double price;
    private List<CartItemDto> items;
    private AddressDto shippingAddress;
    private AddressDto billingAddress;
    private PaymentMethodDto paymentMethod;
    private String email;
    private String username;
    private int state;
}