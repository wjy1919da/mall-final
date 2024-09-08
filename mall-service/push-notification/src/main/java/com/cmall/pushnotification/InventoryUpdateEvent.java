package com.cmall.pushnotification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryUpdateEvent {
    private String type;
    private List<Item> items;
}
