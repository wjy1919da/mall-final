package com.cmall.authservice.payload;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private String accessToken;
    private int userId;
    private String username;
    public JWTAuthResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
