package com.cmall.orderservice.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {
    private String itemId;
    private boolean isInStock;
    // 无参数的默认构造函数
    public InventoryResponse() {
    }

    // 带参数的构造函数
    public InventoryResponse(String itemId, boolean isInStock) {
        this.itemId = itemId;
        this.isInStock = isInStock;
    }

}
