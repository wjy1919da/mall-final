package com.cmall.authservice.service;

import com.cmall.authservice.payload.JWTAuthResponse;
import com.cmall.authservice.payload.LoginDto;
import com.cmall.authservice.payload.RegisterDto;

public interface AuthService {
    public JWTAuthResponse registerNewUser(RegisterDto registerDto);
    public JWTAuthResponse login(LoginDto loginDto);
}
