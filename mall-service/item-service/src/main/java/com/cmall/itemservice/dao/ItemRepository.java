package com.cmall.itemservice.dao;

import com.cmall.itemservice.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
    Page<Item> findAll(Pageable pageable);
}
