package com.cmall.orderservice.payload;

import lombok.Data;

@Data
public class AddressDto {
    private String city;
    private String state;
    private String postalCode;
    private String country;

}