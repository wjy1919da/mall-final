package com.cmall.inventoryservice.controller;

import com.cmall.inventoryservice.payload.InventoryResponse;
import com.cmall.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestBody List<String> itemIds) {
        return inventoryService.isInStock(itemIds);
    }

}
