package com.cmall.inventoryservice.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {
   private String itemId;
   private boolean isInStock;
}
