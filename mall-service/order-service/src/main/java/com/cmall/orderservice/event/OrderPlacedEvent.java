package com.cmall.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private UUID orderId;
    private List<Item> items; // 列表来存储订单中的每个商品和数量

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String itemId;
        private int quantity;
    }
}