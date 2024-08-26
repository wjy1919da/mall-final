package com.cmall.cartservice.payload;

import lombok.Data;

import java.util.Date;

@Data
public class ItemDto {
    private String itemId;
    private String itemName;
    private Double price;
    private Integer inventoryCount;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;
}
