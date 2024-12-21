package com.fredrick.financial_management.request.item;

import com.fredrick.financial_management.enumeration.ItemCategory;
import com.fredrick.financial_management.enumeration.ItemType;
import lombok.Data;

import java.util.Date;

@Data
public class CreateItemRequest {
    private String uuid;
    private String name;
    private int quantity;
    private ItemCategory category;
    private String location;
    private Date date;
    private ItemType itemType;
    private String note;
}
