package com.cmall.accountservice.service.Impl;

import com.cmall.accountservice.dao.AddressRepository;
import com.cmall.accountservice.dao.PaymentMethodRepository;
import com.cmall.accountservice.dao.UserRepository;
import com.cmall.accountservice.entity.Address;
import com.cmall.accountservice.entity.PaymentMethod;
import com.cmall.accountservice.entity.User;
import com.cmall.accountservice.payload.AccountDetailResponse;
import com.cmall.accountservice.payload.AccountUpdateDto;
import com.cmall.accountservice.payload.AddressDto;
import com.cmall.accountservice.payload.PaymentMethodDto;
import com.cmall.accountservice.service.AccountService;
import com.cmall.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public User updateAccountInfo(int userId, AccountUpdateDto accountUpdateDto){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User do not exist!");
        }

        User user = optionalUser.get();
        accountUpdateDto.getShippingAddress().ifPresent(address -> {
            if (address.getAddressId() == 0) {
                address.setUserId(user.getUserId());
                Address savedAddress = addressRepository.save(address);
                user.setShippingAddress(savedAddress.getAddressId());
            }
        });

        accountUpdateDto.getBillingAddress().ifPresent(address -> {
            if (address.getAddressId() == 0) {
                address.setUserId(user.getUserId());
                Address savedAddress = addressRepository.save(address);
                user.setBillingAddress(savedAddress.getAddressId());
            }
        });

        accountUpdateDto.getPaymentMethod().ifPresent(paymentMethod -> {
            if (paymentMethod.getPaymentMethodId() == 0) {
                paymentMethod.setUserId(user.getUserId());
                PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
                user.setPaymentMethodId(savedPaymentMethod.getPaymentMethodId());
            }
        });

        userRepository.save(user);
        return user;
    }

    @Override
    public AccountDetailResponse getUserDetails(int userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User do not exist!");
        }

        User user = optionalUser.get();
        AccountDetailResponse response = new AccountDetailResponse();
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());

        if (user.getShippingAddress() != null) {
            response.setShippingAddress(convertAddress(addressRepository.findById(user.getShippingAddress()).orElse(null)));
        } else {
            response.setShippingAddress(null);
        }

        if (user.getBillingAddress() != null) {
            response.setBillingAddress(convertAddress(addressRepository.findById(user.getBillingAddress()).orElse(null)));
        } else {
            response.setBillingAddress(null);
        }

        if (user.getPaymentMethodId() != null) {
            response.setPaymentMethod(convertPaymentMethod(paymentMethodRepository.findById(user.getPaymentMethodId()).orElse(null)));
        } else {
            response.setPaymentMethod(null);
        }

        return response;
    }
    private AddressDto convertAddress(Address address) {
        if (address == null) return null;
        AddressDto dto = new AddressDto();
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        return dto;
    }

    private PaymentMethodDto convertPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) return null;
        PaymentMethodDto dto = new PaymentMethodDto();
        dto.setCardNumber(paymentMethod.getCardNumber());
        dto.setExpiryDate(paymentMethod.getExpiryDate());
        dto.setCvv(paymentMethod.getCvv());
        dto.setCardholderName(paymentMethod.getCardholderName());
        return dto;
    }

}
