package com.cmall.inventoryservice.service.Impl;

import com.cmall.inventoryservice.dao.InventoryRepository;
import com.cmall.inventoryservice.payload.InventoryResponse;
import com.cmall.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
