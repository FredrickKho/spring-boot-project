package com.fredrick.financial_management.request.item;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateItemRequest {
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
