package com.cmall.accountservice.service;

import com.cmall.accountservice.entity.User;
import com.cmall.accountservice.payload.AccountDetailResponse;
import com.cmall.accountservice.payload.AccountUpdateDto;

public interface AccountService {
    public User updateAccountInfo(int userId, AccountUpdateDto accountUpdateDto);
    public AccountDetailResponse getUserDetails(int userId);
}
