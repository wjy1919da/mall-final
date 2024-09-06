package com.cmall.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class InventoryServceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServceApplication.class, args);
    }
}
