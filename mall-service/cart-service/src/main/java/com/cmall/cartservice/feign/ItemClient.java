package com.cmall.cartservice.feign;

import com.cmall.cartservice.payload.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "item-service") // 这里的 "item-service" 应与 Eureka 上的服务名相同
public interface ItemClient {
    @GetMapping("/api/items/{id}")
    ItemDto getItemById(@PathVariable("id") String itemId);
}
