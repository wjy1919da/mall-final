package com.cmall.cartservice.payload;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class UpdateCartDto {
    private UUID cartId;
    private Map<String, Integer> items;
}
