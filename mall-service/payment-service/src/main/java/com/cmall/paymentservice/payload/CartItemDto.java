package com.cmall.paymentservice.payload;

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