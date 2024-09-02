package com.cmall.paymentservice.payload;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class OrderDto {
    private int userId;
    private Map<String, Integer> items;
    private Double totalPrice;
    private Integer shippingAddressId;
    private Integer billingAddressId;
    private Integer paymentMethodId;
    private String email;
    private String username;
}
