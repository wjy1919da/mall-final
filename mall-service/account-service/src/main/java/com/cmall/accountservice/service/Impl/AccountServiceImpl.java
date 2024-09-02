package com.cmall.accountservice.service.Impl;

import com.cmall.accountservice.dao.AddressRepository;
import com.cmall.accountservice.dao.PaymentMethodRepository;
import com.cmall.accountservice.dao.UserRepository;
import com.cmall.accountservice.entity.Address;
import com.cmall.accountservice.entity.PaymentMethod;
import com.cmall.accountservice.entity.User;
import com.cmall.accountservice.payload.AccountUpdateDto;
import com.cmall.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    public User updateAccountInfo(User user, AccountUpdateDto accountUpdateDto){
        // 更新或添加新的送货地址
        accountUpdateDto.getShippingAddress().ifPresent(address -> {
            // 检查是否已存在地址ID，如果不存在则认为是新地址
            if (address.getAddressId() == 0) {
                address.setUserId(user.getUserId());
                Address savedAddress = addressRepository.save(address);
                user.setShippingAddress(savedAddress.getAddressId());
            }
        });

        // 更新或添加新的账单地址
        accountUpdateDto.getBillingAddress().ifPresent(address -> {
            // 检查是否已存在地址ID，如果不存在则认为是新地址
            if (address.getAddressId() == 0) {
                address.setUserId(user.getUserId());
                Address savedAddress = addressRepository.save(address);
                user.setBillingAddress(savedAddress.getAddressId());
            }
        });

        // 更新或添加新的支付方式
        accountUpdateDto.getPaymentMethod().ifPresent(paymentMethod -> {
            // 检查是否已存在支付方法ID，如果不存在则认为是新支付方法
            if (paymentMethod.getPaymentMethodId() == 0) {
                paymentMethod.setUserId(user.getUserId());
                PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
                user.setPaymentMethodId(savedPaymentMethod.getPaymentMethodId());
            }
        });

        userRepository.save(user);
        return user;
    }

}
