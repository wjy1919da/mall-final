package com.cmall.itemservice.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Document(collection = "items")
public class Item {
    @Id
    private String itemId;

    @NotBlank(message = "Item name must not be empty")
    @Size(max = 20, message = "Item name must be less than 20 characters")
    @Field(name = "itemName")
    private String itemName;

    @Field(name = "price")
    private Double price;

    @Field(name = "inventoryCount")
    private Integer inventoryCount;

    @Min(value = 1, message = "Status must be either 1, 2, or 3")
    @Max(value = 3, message = "Status must be either 1, 2, or 3")
    @Field(name = "status")
    private Integer status;

    @CreatedDate
    @Field(name = "createdAt")
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updatedAt")
    private Date updatedAt;
}
