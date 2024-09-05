package com.cmall.paymentservice.feign;

import com.cmall.common.payload.AccountDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service")
public interface UserClient {
    @GetMapping("/api/account/{id}")
    AccountDetailResponse getUserDetails(@PathVariable("id") int userId);
}
