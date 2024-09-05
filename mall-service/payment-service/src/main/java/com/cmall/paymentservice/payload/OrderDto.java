package com.cmall.paymentservice.payload;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class OrderDto {
    private Integer userId;
    private Double totalPrice;
    private List<CartItemDto> items;
    private Integer shippingAddressId;
    private Integer billingAddressId;
    private Integer paymentMethodId;
    private String email;
    private String username;
}
