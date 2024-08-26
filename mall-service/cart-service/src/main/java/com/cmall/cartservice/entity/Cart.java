package com.cmall.cartservice.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Data
@Table("Carts")
public class Cart {
    @PrimaryKey
    private UUID cartId;
    private Integer userId;
    private Map<String, Integer> items; // Item ID as key, quantity as value
    private Double price;
    private Date updatedAt;
    private Date createdAt;

    public Cart(int userId, Map<String, Integer> items, double price) {
        this.cartId = UUID.randomUUID(); // 自动生成唯一的 UUID
        this.userId = userId;
        this.items = items;
        this.price = price;
        this.updatedAt = new Date(); // 假设 updatedAt 和 createdAt 使用 Java 的 Date
        this.createdAt = new Date();
    }
}
