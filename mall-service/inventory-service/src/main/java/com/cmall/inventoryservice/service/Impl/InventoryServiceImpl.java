package com.cmall.inventoryservice.service.Impl;

import com.cmall.inventoryservice.dao.InventoryRepository;
import com.cmall.inventoryservice.entity.Inventory;
import com.cmall.inventoryservice.event.InventoryUpdateEvent;
import com.cmall.inventoryservice.payload.InventoryResponse;
import com.cmall.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private  InventoryRepository inventoryRepository;

    @Override
    public List<InventoryResponse> isInStock(List<String> itemIds){
        return inventoryRepository.findByItemIdIn(itemIds).stream().map(
                inventory -> InventoryResponse.builder()
                                .itemId(inventory.getItemId())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()
        ).toList();
    }

    @Transactional
    @Override
    public void updateInventory(List<InventoryUpdateEvent.Item> items, String type) {
        items.forEach(item -> {
            Inventory inventory = inventoryRepository.findByItemId(item.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found: " + item.getItemId()));
            if ("increase".equals(type)) {
                inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
            } else if ("decrease".equals(type)) {
                inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
                if (inventory.getQuantity() < 0) {
                    throw new RuntimeException("Insufficient stock for item: " + item.getItemId());
                }
            }
            inventoryRepository.save(inventory);
        });
    }

}
