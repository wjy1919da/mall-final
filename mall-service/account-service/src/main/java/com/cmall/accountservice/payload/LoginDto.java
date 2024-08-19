package com.cmall.accountservice.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}