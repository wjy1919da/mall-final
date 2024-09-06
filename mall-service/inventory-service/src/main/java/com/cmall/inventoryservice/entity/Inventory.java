package com.cmall.inventoryservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String itemId;
    private Integer quantity;
}
