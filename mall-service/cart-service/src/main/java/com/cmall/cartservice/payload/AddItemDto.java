package com.cmall.cartservice.payload;

import lombok.Data;

@Data
public class AddItemDto {
    private String itemId;
    private Integer itemCount;
}
