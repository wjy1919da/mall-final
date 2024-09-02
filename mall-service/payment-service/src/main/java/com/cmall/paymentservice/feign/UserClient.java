package com.cmall.paymentservice.feign;

import com.cmall.common.payload.AccountDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "account-service")
public interface UserClient {
    @GetMapping("/api/account/{id}")
    AccountDetailResponse getUserDetails(int userId);
}
