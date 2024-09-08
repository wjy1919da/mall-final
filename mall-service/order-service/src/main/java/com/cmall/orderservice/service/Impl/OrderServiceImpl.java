package com.cmall.orderservice.service.Impl;

import com.cmall.orderservice.dao.OrderRepository;
import com.cmall.orderservice.entity.Order;
import com.cmall.orderservice.event.OrderPlacedEvent;
import com.cmall.orderservice.payload.*;
import com.cmall.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import reactor.core.publisher.Mono;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private KafkaTemplate kafkaTemplate;


    private WebClient userClient() {
        return webClientBuilder.baseUrl("http://account-service").build(); // Adjusted to match your Feign client's service ID
    }

    private WebClient cartClient() {
        return webClientBuilder.baseUrl("http://cart-service").build(); // Adjusted to match your Feign client's service ID
    }

    private WebClient inventoryClient(){
        return webClientBuilder.baseUrl("http://inventory-service").build();
    }

    @Override
    public Mono<Order> createOrder(int userId) {
        Mono<AccountDetailResponse> userInfo = userClient().get()
                .uri("/api/account/{id}", userId)
                .retrieve()
                .bodyToMono(AccountDetailResponse.class);

        Mono<CartDto> cartInfo = cartClient().get()
                .uri("/api/cart/{userId}", userId)
                .retrieve()
                .bodyToMono(CartDto.class);

        return Mono.zip(userInfo, cartInfo)
                .flatMap(tuple -> {
                    AccountDetailResponse user = tuple.getT1();
                    CartDto cart = tuple.getT2();
                    List<String> itemIds = cart.getItems().stream()
                            .map(CartItemDto::getItemId)
                            .collect(Collectors.toList());

                    return checkInventory(itemIds)
                            .map(inStock -> {
                                if (inStock.contains(false)) {
                                    throw new RuntimeException("One or more items are out of stock");
                                }
                                return tuple;
                            });
                })
                .map(tuple -> {
                    AccountDetailResponse user = tuple.getT1();
                    CartDto cart = tuple.getT2();
                    OrderDto orderDto = new OrderDto();
                    modelMapper.map(user, orderDto);
                    modelMapper.map(cart, orderDto);
                    Order order = new Order(cart.getTotalPrice(), user.getEmail(), user.getUsername(), cart.getUserId(), 1);
                    order.setItems(cart.getItems().stream()
                            .collect(Collectors.toMap(CartItemDto::getItemId, CartItemDto::getQuantity)));

                    order.setShippingAddress(addressToMap(orderDto.getShippingAddress()));
                    order.setBillingAddress(addressToMap(orderDto.getBillingAddress()));
                    order.setPaymentMethod(paymentMethodToMap(orderDto.getPaymentMethod()));

                    List<OrderPlacedEvent.Item> eventItems = cart.getItems().stream()
                            .map(item -> new OrderPlacedEvent.Item(item.getItemId(), item.getQuantity()))
                            .collect(Collectors.toList());

                    OrderPlacedEvent event = new OrderPlacedEvent(order.getOrderId(), eventItems);
                    kafkaTemplate.send("notificationTopic", event);
                    return order;
                })
                .flatMap(orderRepository::save);
    }
    private Mono<List<Boolean>> checkInventory(List<String> itemIds) {
        return inventoryClient().post()
                .uri("/api/inventory")
                .bodyValue(itemIds)
                .retrieve()
                .bodyToFlux(InventoryResponse.class)
                .map(InventoryResponse::isInStock)
                .collectList();
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
