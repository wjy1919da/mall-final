package com.cmall.cartservice.service.Impl;

import com.cmall.cartservice.dao.CartRepository;
import com.cmall.cartservice.entity.Cart;
import com.cmall.cartservice.feign.ItemClient;
import com.cmall.cartservice.payload.*;
import com.cmall.cartservice.service.CartService;
import com.cmall.common.exception.ApiException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemClient itemClient;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Cart addItemToCart(Integer userId, AddItemDto item){
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> new Cart(userId, new HashMap<>(), 0.0));

        // 确保 items 映射已经初始化
        if (cart.getItems() == null) {
            cart.setItems(new HashMap<>());
        }

        // 从 item-service 获取商品信息
        ItemDto itemInfo = itemClient.getItemById(item.getItemId());
        if (itemInfo == null) {
            throw new ApiException("Item not found", HttpStatus.NOT_FOUND);
        }

        // 更新或添加商品到购物车
        cart.getItems().merge(item.getItemId(), item.getItemCount(), Integer::sum);

        // 重新计算购物车总价
        double newTotalPrice = cart.getItems().entrySet().stream()
                .mapToDouble(entry -> {
                    // 假设有一个方法来获取每个 itemId 的价格，这里使用 itemInfo.getPrice() 作为示例
                    double pricePerItem = itemInfo.getPrice(); // 实际应用中，你可能需要根据 entry.getKey() 来动态获取价格
                    return pricePerItem * entry.getValue();
                }).sum();

        // 更新购物车的价格和更新时间
        cart.setPrice(newTotalPrice);
        cart.setUpdatedAt(new Date());

        // 保存更新后的购物车到数据库
        Cart updatedCart = cartRepository.save(cart);

        return updatedCart;
    }

    @Override
    public CartDto getCartDetailsByUserId(Integer userId){
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException("Cart not found",HttpStatus.NOT_FOUND));
        if (cart.getItems() == null) {
            cart.setItems(new HashMap<>());
        }
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getCartId());
        cartDto.setUserId(cart.getUserId());
        cartDto.setTotalPrice(cart.getPrice());
        List<CartItemDto> itemDtos = cart.getItems().entrySet().stream()
                .map(entry -> {
                    ItemDto item = itemClient.getItemById(entry.getKey());
                    CartItemDto cartItemDto = new CartItemDto();
                    cartItemDto.setQuantity(entry.getValue());
                    modelMapper.map(item, cartItemDto);
                    return cartItemDto;
                }).collect(Collectors.toList());
        cartDto.setItems(itemDtos);
        return cartDto;
    }

    @Override
    public Cart removeItemFromCart(Integer userId, String itemId){
        // Retrieve the cart for the specified user or throw an exception if not found
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException("Cart not found with user id " + userId, HttpStatus.NOT_FOUND));

        // Check if the item exists in the cart, throw an exception if not found
        if (!cart.getItems().containsKey(itemId)) {
            throw new ApiException("Item not found in cart: " + itemId, HttpStatus.NOT_FOUND);
        }

        // Remove the item from the cart
        cart.getItems().remove(itemId);

        // Recalculate the total price
        double totalPrice = 0.0;
        for (Map.Entry<String, Integer> entry : cart.getItems().entrySet()) {
            String key = entry.getKey();
            Integer quantity = entry.getValue();
            // Assuming getItemById returns an ItemDto with the price of the item
            ItemDto item = itemClient.getItemById(key);
            totalPrice += item.getPrice() * quantity;
        }
        cart.setPrice(totalPrice);

        // Update the timestamp
        cart.setUpdatedAt(new Date());

        // Save and return the updated cart
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(UpdateCartDto updateCartDto) {
        Cart cart = cartRepository.findById(updateCartDto.getCartId())
                .orElseThrow(() -> new ApiException("Cart not found with ID: " + updateCartDto.getCartId(), HttpStatus.NOT_FOUND));

        // 获取旧的购物车信息以用于计算价格
        Map<String, Integer> oldItems = cart.getItems();
        double totalPrice = 0;

        // 更新购物车条目
        Map<String, Integer> updatedItems = updateCartDto.getItems();
        for (Map.Entry<String, Integer> entry : updatedItems.entrySet()) {
            String itemId = entry.getKey();
            Integer quantity = entry.getValue();

            // 假设 getItemPrice 返回商品的当前价格
            double itemPrice = itemClient.getItemById(itemId).getPrice();
            totalPrice += itemPrice * quantity;
        }

        // 设置更新后的商品列表和总价
        cart.setItems(updatedItems);
        cart.setPrice(totalPrice);
        cart.setUpdatedAt(new Date());

        return cartRepository.save(cart);
    }
}
