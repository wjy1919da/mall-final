package com.cmall.cartservice.config;

import com.cmall.common.config.ModelMapperConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients
@Import(ModelMapperConfig.class)
public class FeignConfig {
}