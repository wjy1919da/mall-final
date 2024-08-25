package com.cmall.accountservice.payload;

import lombok.Data;

@Data
public class AccountDetailResponse {
    private String email;
    private String username;
    private AddressDto shippingAddress;
    private AddressDto billingAddress;
    private PaymentMethodDto paymentMethod;
}
