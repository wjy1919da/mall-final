package com.cmall.itemservice.controller;

import com.cmall.itemservice.entity.Item;
import com.cmall.itemservice.payload.ItemDto;
import com.cmall.itemservice.payload.ItemStatusUpdateDto;
import com.cmall.itemservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody ItemDto itemDto) {
        Item newItem = itemService.addItem(itemDto);
        return ResponseEntity.ok(newItem);
    }

    @GetMapping
    public ResponseEntity<Page<ItemDto>> getAllItems(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "5" ) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ItemDto> items = itemService.getAllItems(pageable);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable String id) {
        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItemStatus(@PathVariable String id, @RequestBody ItemStatusUpdateDto updateDto) {
        itemService.updateItemStatus(id, updateDto.getStatus());
        return ResponseEntity.ok("item status update success!");
    }






}
