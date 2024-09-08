package com.cmall.pushnotification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@EnableEurekaClient
@SpringBootApplication
@Slf4j
public class PushNotificationApplication {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(PushNotificationApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        log.info("Received notification for order: " + orderPlacedEvent.getOrderId());

        // 构建 InventoryUpdateEvent
        InventoryUpdateEvent updateEvent = new InventoryUpdateEvent(
                "decrease",
                orderPlacedEvent.getItems()
        );

        // 发送 InventoryUpdateEvent 到 inventoryUpdateTopic
        kafkaTemplate.send("inventoryUpdateTopic", updateEvent);
        log.info("Sent inventory update event for order: " + orderPlacedEvent.getOrderId());
    }


}
