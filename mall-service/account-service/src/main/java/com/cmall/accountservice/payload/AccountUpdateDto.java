package com.cmall.accountservice.payload;

import com.cmall.accountservice.entity.Address;
import com.cmall.accountservice.entity.PaymentMethod;
import lombok.Data;

import java.util.Optional;

@Data
public class AccountUpdateDto {
    private Optional<Address> shippingAddress = Optional.empty();
    private Optional<Address> billingAddress =  Optional.empty();
    private Optional<PaymentMethod> paymentMethod = Optional.empty();
}
