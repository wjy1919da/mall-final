package com.cmall.orderservice.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@Table("orders")
public class Order {
    @PrimaryKey
//    @Column("order_id")
    private UUID orderId;

    //    @Column("user_id")
    private int userId;

    //    @Column("items")
    private Map<String, Integer> items;

    //    @Column("price")
    private Double price;

    //    @Column("shipping_address")
    private Map<String, String> shippingAddress;

    //    @Column("billing_address")
    private Map<String, String> billingAddress;

    //    @Column("payment_method")
    private Map<String, String> paymentMethod;

    //    @Column("email")
    private String email;

    //    @Column("username")
    private String username;

    //    @Column("created_at")
    private Date createdAt;

    //    @Column("update_at")
    private Date updateAt;

    private int state;

    public Order(Double price, String email, String username, int userId, int state) {
        this.orderId = UUID.randomUUID();
        this.createdAt = new Date();
        this.updateAt = new Date();
        this.price = price;
        this.email = email;
        this.username = username;
        this.userId = userId;
        this.state = state;
    }
}