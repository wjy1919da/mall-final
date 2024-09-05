package com.cmall.cartservice.controller;

import com.cmall.cartservice.entity.Cart;
import com.cmall.cartservice.payload.AddItemDto;
import com.cmall.cartservice.payload.CartDto;
import com.cmall.cartservice.payload.RemoveItemsDto;
import com.cmall.cartservice.payload.UpdateCartDto;
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
        System.out.println("getCartByUserId is called");
        CartDto cartDto = cartService.getCartDetailsByUserId(userId);
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/{userId}/item/{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Integer userId, @PathVariable String itemId) {
        Cart updatedCart = cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Integer userId, @RequestBody UpdateCartDto updateCartDto) {
        Cart updatedCart = cartService.updateCart(updateCartDto);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{userId}/items")
    public ResponseEntity<Cart> removeItemsFromCart(@PathVariable Integer userId, @RequestBody RemoveItemsDto removeItemsDto) {
        Cart updatedCart = cartService.removeItemsFromCart(userId, removeItemsDto);
        return ResponseEntity.ok(updatedCart);
    }

}
