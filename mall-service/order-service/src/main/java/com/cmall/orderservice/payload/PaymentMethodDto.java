package com.cmall.orderservice.payload;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentMethodDto {
    private String cardNumber;
    private Date expiryDate;
    private String cvv;
    private String cardholderName;
}