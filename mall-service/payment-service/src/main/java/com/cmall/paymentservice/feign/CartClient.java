package com.cmall.paymentservice.feign;


import com.cmall.paymentservice.payload.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service")
public interface CartClient {
    @GetMapping("/api/cart/{userId}")
    CartDto getCartByUserId(@PathVariable("userId") int userId);
}
