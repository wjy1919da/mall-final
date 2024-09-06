package com.cmall.inventoryservice.service;


import com.cmall.inventoryservice.dao.InventoryRepository;
import com.cmall.inventoryservice.payload.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InventoryService {
    public List<InventoryResponse> isInStock(List<String> itemIds);
}
