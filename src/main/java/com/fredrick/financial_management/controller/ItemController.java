package com.fredrick.financial_management.controller;

import com.fredrick.financial_management.entity.Item;
import com.fredrick.financial_management.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("")
    public List<Item> getItem(){
        List<Item> items = itemService.findAll();
        return items;
    }


}
