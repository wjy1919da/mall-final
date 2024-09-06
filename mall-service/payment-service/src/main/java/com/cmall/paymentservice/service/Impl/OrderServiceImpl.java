package com.cmall.paymentservice.service.Impl;

import com.cmall.common.exception.ApiException;
import com.cmall.common.payload.AccountDetailResponse;
import com.cmall.paymentservice.dao.OrderRepository;
import com.cmall.paymentservice.entity.Order;
import com.cmall.paymentservice.feign.CartClient;
import com.cmall.paymentservice.feign.UserClient;
import com.cmall.paymentservice.payload.*;
import com.cmall.paymentservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserClient userClient;

    @Autowired
    private CartClient cartClient;

    @Override
    public Order createOrder(int userId){
        AccountDetailResponse userInfo = userClient.getUserDetails(userId);
        CartDto cartInfo = cartClient.getCartByUserId(userId);
        OrderDto orderDto = new OrderDto();
        modelMapper.map(userInfo, orderDto);
        modelMapper.map(cartInfo, orderDto);
        Order order = new Order(cartInfo.getTotalPrice(), userInfo.getEmail(), userInfo.getUsername(), cartInfo.getUserId());
        order.setItems(cartInfo.getItems().stream()
                .collect(Collectors.toMap(CartItemDto::getItemId, CartItemDto::getQuantity)));
        order.setShippingAddress(addressToMap(orderDto.getShippingAddress()));
        order.setBillingAddress(addressToMap(orderDto.getBillingAddress()));
        order.setPaymentMethod(paymentMethodToMap(orderDto.getPaymentMethod()));
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    private Map<String, String> addressToMap(AddressDto address) {
        return Optional.ofNullable(address).map(a -> {
            Map<String, String> map = new HashMap<>();

            String state = Optional.ofNullable(a.getState())
                    .orElseThrow(() -> new IllegalArgumentException("State is missing"));
            String city = Optional.ofNullable(a.getCity())
                    .orElseThrow(() -> new IllegalArgumentException("City is missing"));
            String postalCode = Optional.ofNullable(a.getPostalCode())
                    .orElseThrow(() -> new IllegalArgumentException("Postal code is missing"));
            String country = Optional.ofNullable(a.getCountry())
                    .orElseThrow(() -> new IllegalArgumentException("Country is missing"));

            map.put("state", state);
            map.put("city", city);
            map.put("postalCode", postalCode);
            map.put("country", country);

            return map;
        }).orElseThrow(() -> new IllegalArgumentException("Address data is missing"));
    }

    private Map<String, String> paymentMethodToMap(PaymentMethodDto paymentMethod) {
        Optional<PaymentMethodDto> optionalPaymentMethod = Optional.ofNullable(paymentMethod);

        return optionalPaymentMethod.map(pm -> {
            Map<String, String> map = new HashMap<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            String cardNumber = Optional.ofNullable(pm.getCardNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Card number is missing"));
            String cardholderName = Optional.ofNullable(pm.getCardholderName())
                    .orElseThrow(() -> new IllegalArgumentException("Cardholder name is missing"));
            String cvv = Optional.ofNullable(pm.getCvv())
                    .orElseThrow(() -> new IllegalArgumentException("CVV is missing"));

            map.put("cardNumber", cardNumber);
            map.put("cardholderName", cardholderName);
            map.put("cvv", cvv);

            Date expiryDate = Optional.ofNullable(pm.getExpiryDate())
                    .orElseThrow(() -> new IllegalArgumentException("Expiry date is missing"));
            map.put("expiryDate", formatter.format(expiryDate));

            return map;
        }).orElseThrow(() -> new IllegalArgumentException("Payment method data is missing"));
    }
}
