package com.cmall.accountservice.controller;

import com.cmall.accountservice.entity.User;
import com.cmall.accountservice.payload.AccountUpdateDto;
import com.cmall.accountservice.service.AccountService;
import com.cmall.authservice.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
//        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        User updatedUser = accountService.updateAccountInfo(user, accountUpdateDto);
//        return ResponseEntity.ok(updatedUser);
//    }
}
