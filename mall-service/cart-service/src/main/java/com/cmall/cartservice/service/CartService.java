package com.cmall.cartservice.service;

import com.cmall.cartservice.entity.Cart;
import com.cmall.cartservice.payload.AddItemDto;
import com.cmall.cartservice.payload.CartDto;

public interface CartService {
    Cart addItemToCart(Integer userId, AddItemDto item);
    CartDto getCartDetailsByUserId(Integer userId);

}
