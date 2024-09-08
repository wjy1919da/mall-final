package com.cmall.inventoryservice;

import com.cmall.inventoryservice.event.InventoryUpdateEvent;
import com.cmall.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.KafkaListener;
@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class InventoryServceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServceApplication.class, args);
    }

    @Autowired
    InventoryService inventoryService;


    @KafkaListener(topics = "inventoryUpdateTopic")
    public void handleNotification(InventoryUpdateEvent inventoryUpdateEvent) {
        log.info("Received inventory update event");
        try {
            inventoryService.updateInventory(inventoryUpdateEvent.getItems(), inventoryUpdateEvent.getType());
            log.info("Inventory updated for type: {}", inventoryUpdateEvent.getType());
        } catch (RuntimeException e) {
            log.error("Error updating inventory: {}", e.getMessage(), e);
        }
    }
}
