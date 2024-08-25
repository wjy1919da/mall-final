package com.cmall.itemservice.service;

import com.cmall.itemservice.entity.Item;
import com.cmall.itemservice.payload.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ItemService {
    public Item addItem(ItemDto itemDto);
    public Page<ItemDto> getAllItems(Pageable pageable);
    public ItemDto getItemById(String id);
    public void updateItemStatus(String itemId, Integer status);
}
