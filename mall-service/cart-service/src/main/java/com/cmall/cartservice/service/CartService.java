package com.cmall.cartservice.service;

import com.cmall.cartservice.entity.Cart;
import com.cmall.cartservice.payload.AddItemDto;
import com.cmall.cartservice.payload.CartDto;
import com.cmall.cartservice.payload.RemoveItemsDto;
import com.cmall.cartservice.payload.UpdateCartDto;

public interface CartService {
    Cart addItemToCart(Integer userId, AddItemDto item);
    CartDto getCartDetailsByUserId(Integer userId);
    Cart removeItemFromCart(Integer userId, String itemId);
    Cart updateCart(UpdateCartDto updateCartDto);
    Cart removeItemsFromCart(Integer userId, RemoveItemsDto removeItemsDto);
}
