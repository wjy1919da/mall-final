package com.cmall.accountservice.controller;

import com.cmall.accountservice.entity.User;
import com.cmall.accountservice.payload.AccountDetailResponse;
import com.cmall.accountservice.payload.AccountUpdateDto;
import com.cmall.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PutMapping("/{id}")
    public ResponseEntity<User> updateAccountInfo(@PathVariable int id, @RequestBody AccountUpdateDto accountUpdateDto){
        User updatedUser = accountService.updateAccountInfo(id, accountUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailResponse> getUserDetails(@PathVariable int id) {
        AccountDetailResponse accountDetails = accountService.getUserDetails(id);
        return ResponseEntity.ok(accountDetails);
    }

}
