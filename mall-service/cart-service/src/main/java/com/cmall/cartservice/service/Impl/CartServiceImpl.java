package com.cmall.cartservice.service.Impl;

import com.cmall.cartservice.dao.CartRepository;
import com.cmall.cartservice.entity.Cart;
import com.cmall.cartservice.feign.ItemClient;
import com.cmall.cartservice.payload.ItemDto;
import com.cmall.cartservice.payload.AddItemDto;
import com.cmall.cartservice.service.CartService;
import com.cmall.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemClient itemClient;

    @Override
    public Cart addItemToCart(Integer userId, AddItemDto item){
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> new Cart(userId, new HashMap<>(), 0.0));
        ItemDto itemInfo = itemClient.getItemById(item.getItemId());
        if (itemInfo == null) {
            throw new ApiException("item not found ", HttpStatus.NOT_FOUND);
        }
        double itemPrice = itemInfo.getPrice()*item.getItemCount();
        if(cart.getItems().containsKey(item.getItemId())){
            cart.getItems().put(item.getItemId(), cart.getItems().get(item.getItemId()) + item.getItemCount());
        }else{
            cart.getItems().put(item.getItemId(), item.getItemCount());
            cart.setCreatedAt(new Date());
        }
        cart.setPrice(cart.getPrice()+itemPrice);
        cart.setUpdatedAt(new Date());
        Cart updatedCart = cartRepository.save(cart);
        return updatedCart;
    }
}
