package com.cmall.cartservice.payload;

import lombok.Data;

import java.util.List;

@Data
public class RemoveItemsDto {
    private List<String> itemIds;
}
