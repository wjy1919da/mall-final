package com.cmall.pushnotification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.KafkaListener;

@EnableEurekaClient
@SpringBootApplication
@Slf4j
public class PushNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PushNotificationApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        log.info("Received notification for order::  "+ orderPlacedEvent.getOrderId());
    }

}
