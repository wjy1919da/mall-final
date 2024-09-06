package com.cmall.orderservice.payload;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
    private UUID cartId;
    private Integer userId;
    private Double totalPrice;
    private List<CartItemDto> items;
}
