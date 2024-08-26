package com.cmall.cartservice.service;

import com.cmall.cartservice.entity.Cart;
import com.cmall.cartservice.payload.AddItemDto;

public interface CartService {
    public Cart addItemToCart(Integer userId, AddItemDto item);
}
