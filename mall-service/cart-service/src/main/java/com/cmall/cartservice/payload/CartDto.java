package com.cmall.cartservice.payload;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class CartDto {
    private UUID cartId;
    private Integer userId;
    private Double totalPrice;
    private List<CartItemDto> items;
}
