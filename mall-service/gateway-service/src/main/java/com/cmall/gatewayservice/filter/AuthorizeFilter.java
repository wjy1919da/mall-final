package com.cmall.gatewayservice.filter;

import com.cmall.gatewayservice.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
//
//@Component
//public class AuthorizeFilter implements GlobalFilter, Ordered {
//
//    private static final String AUTHORIZE_TOKEN = "Authorization";
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    private String getJWTfromRequest(ServerHttpRequest request) {
//        String bearerToken = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
//
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//
//        if (request.getURI().getPath().startsWith("/api/auth/signin") ||
//                request.getURI().getPath().startsWith("/api/auth/signup")) {
//            return chain.filter(exchange);
//        }
//
//        String token = getJWTfromRequest(request);
//        System.out.println("Token "+ token);
//        if (token == null) {
//            System.out.println("token is null");
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }
//        Mono<Boolean> validation = jwtTokenUtil.validateToken(token);
//
//        // 处理验证结果
//        return validation.flatMap(isValid -> {
//            if (Boolean.TRUE.equals(isValid)) {
//                System.out.println("Token is valid");
//                return chain.filter(exchange);
//            } else {
//                System.out.println("Token is not valid");
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.setComplete();
//            }
//        }).onErrorResume(e -> {
//            System.out.println("Error occurs: " + e.getMessage());
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        });
//    }
//
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
