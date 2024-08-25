package com.cmall.itemservice.service.Impl;

import com.cmall.common.exception.ApiException;
import com.cmall.common.exception.ResourceNotFoundException;
import com.cmall.itemservice.dao.ItemRepository;
import com.cmall.itemservice.entity.Item;
import com.cmall.itemservice.payload.ItemDto;
import com.cmall.itemservice.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Override
    public Item addItem(ItemDto itemDto) {
        Item item = new Item();
        modelMapper.map(itemDto, item);
        item.setCreatedAt(new Date());  // Manually setting creation date
        item.setUpdatedAt(new Date());
        return itemRepository.save(item);
    }

    @Override
    public Page<ItemDto> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable)
                .map(item -> modelMapper.map(item, ItemDto.class));
    }

    @Override
    public ItemDto getItemById(String id){
        return itemRepository.findById(id)
                .map(item -> modelMapper.map(item, ItemDto.class))
                .orElseThrow(() -> new ApiException("item not found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public void updateItemStatus(String itemId, Integer status){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ApiException("Item not found with id: " + itemId, HttpStatus.NOT_FOUND));
        item.setStatus(status);
        itemRepository.save(item);
    }


}
