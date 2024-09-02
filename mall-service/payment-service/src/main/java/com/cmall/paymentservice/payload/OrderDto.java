package com.cmall.paymentservice.payload;

import java.util.Map;
import java.util.UUID;

public class OrderDto {
    private UUID userId;
    private Map<String, Integer> items;
    private Double totalPrice;
    private Integer shippingAddressId;
    private Integer billingAddressId;
    private Integer paymentMethodId;
    private String email;
    private String username;
}
