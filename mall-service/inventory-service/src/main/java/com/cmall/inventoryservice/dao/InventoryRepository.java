package com.cmall.inventoryservice.dao;

import com.cmall.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findByItemIdIn(List<String> itemIds);
    Optional<Inventory> findByItemId(String itemId);
}
