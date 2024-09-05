package com.cmall.paymentservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@Table("Orders")
@Builder
@AllArgsConstructor
public class Order {
    @PrimaryKey
    @Builder.Default
    private UUID orderId = UUID.randomUUID();

    @Column("user_id")
    private int userId;

    @Column("items")
    private Map<String, Integer> items;

    @Column("total_price")
    private Double price;

    @Column("shipping_address")
    private Map<String, String> shippingAddress;

    @Column("billing_address")
    private Map<String, String> billingAddress;

    @Column("payment_method")
    private Map<String, String> paymentMethod;

    @Column("email")
    private String email;

    @Column("username")
    private String username;

    @Column("created_at")
    private Date createdAt;

    @Column("update_at")
    private Date updateAt;

    public Order(){
        this.orderId = UUID.randomUUID();
        this.createdAt = new Date();
        this.updateAt = new Date();
    }
}
