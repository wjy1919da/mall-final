package com.cmall.paymentservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;
import java.util.UUID;

@Data
@Table("Orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @PrimaryKey
    @Builder.Default
    private UUID orderId = UUID.randomUUID();

    @Column("user_id")
    private UUID userId;

    @Column("items")
    private Map<String, Integer> items;

    @Column("total_price")
    private Double price;

    @Column("shipping_address_id")
    private Integer shippingAddressId;

    @Column("billing_address_id")
    private Integer billingAddressId;

    @Column("payment_method_id")
    private Integer paymentMethodId;

    @Column("email")
    private String email;

    @Column("username")
    private String username;

    @Column("created_at")
    private long createdAt;
}
