package com.cmall.accountservice.controller;

import com.cmall.accountservice.entity.User;
import com.cmall.accountservice.payload.AccountUpdateDto;
import com.cmall.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

//    @PutMapping("/account")
//    public ResponseEntity<User> updateAccountInfo(@RequestBody AccountUpdateDto accountUpdateDto){
//
//    }
}
