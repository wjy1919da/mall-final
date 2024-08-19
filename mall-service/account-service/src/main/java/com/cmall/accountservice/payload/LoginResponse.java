package com.cmall.accountservice.payload;

import lombok.Data;

@Data
public class LoginResponse {
    int userId;
    String email;
    String token;
}