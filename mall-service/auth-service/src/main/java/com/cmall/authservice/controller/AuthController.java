package com.cmall.authservice.controller;

import com.cmall.authservice.payload.JWTAuthResponse;
import com.cmall.authservice.payload.LoginDto;
import com.cmall.authservice.payload.RegisterDto;
import com.cmall.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        JWTAuthResponse jwtAuthResponse = authService.registerNewUser(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtAuthResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto){
        JWTAuthResponse jwtAuthResponse = authService.login(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(jwtAuthResponse);
    }

}
