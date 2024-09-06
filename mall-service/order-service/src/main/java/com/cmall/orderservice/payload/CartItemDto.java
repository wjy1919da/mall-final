package com.cmall.orderservice.payload;

import lombok.Data;

@Data
public class CartItemDto {
    private String itemId;
    private String itemName;
    private Double price;
    private Integer inventoryCount;
    private Integer status;
    private Integer quantity;
}