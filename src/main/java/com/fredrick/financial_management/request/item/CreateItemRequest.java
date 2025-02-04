package com.fredrick.financial_management.request.item;

import com.fredrick.financial_management.enumeration.ItemCategory;
import com.fredrick.financial_management.enumeration.ItemType;
import lombok.Data;

import java.util.Date;

@Data
public class CreateItemRequest {
    private String uuid;
    private String name;
    private String quantity;
    private String totalPrice;
    private String category;
    private String location;
    private String date;
    private String type;
    private String note;
}
