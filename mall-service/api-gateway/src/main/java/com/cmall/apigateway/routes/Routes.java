package com.cmall.apigateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> itemServiceRoute() {
        return GatewayRouterFunctions.route("item-service")
                .route(RequestPredicates.path("/api/items"), HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceCircuitBreaker"))
                .build();
    }
}
