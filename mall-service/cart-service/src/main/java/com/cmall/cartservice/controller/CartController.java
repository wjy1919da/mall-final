package com.cmall.cartservice.controller;

import com.cmall.cartservice.entity.Cart;
import com.cmall.cartservice.payload.AddItemDto;
import com.cmall.cartservice.payload.CartDto;
import com.cmall.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> addItemToCart(@PathVariable Integer userId, @RequestBody AddItemDto addItemDto){
        Cart updatedCart =  cartService.addItemToCart(userId, addItemDto);
        return ResponseEntity.ok(updatedCart);
    }

    /**
     *  Get cart details
     * */
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable Integer userId) {
        CartDto cartDto = cartService.getCartDetailsByUserId(userId);
        return ResponseEntity.ok(cartDto);
    }

    



}
