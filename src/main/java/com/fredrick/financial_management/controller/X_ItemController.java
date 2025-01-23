package com.fredrick.financial_management.controller;

import com.fredrick.financial_management.entity.Item;
import com.fredrick.financial_management.enumeration.ItemCategory;
import com.fredrick.financial_management.request.item.CreateItemRequest;
import com.fredrick.financial_management.request.item.UpdateItemRequest;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/x-item")
public class X_ItemController {
    private ItemService itemService;

    @Autowired
    public X_ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<Item>>> getItem(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String date,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(itemService.findAll(category, type, date, page, size));
    }

    @PostMapping("/{uuid}/create")
    public ResponseEntity<Response<String>> createItem(@PathVariable String uuid,
            @RequestBody CreateItemRequest request) {
        return ResponseEntity.ok(itemService.xCreate(uuid, request));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Response<String>> updateItem(@PathVariable String id,
            @RequestBody UpdateItemRequest request) {
        return ResponseEntity.ok(itemService.xUpdate(id, request));
    }
    @GetMapping("/category")
    public ResponseEntity<Response<List<String>>> getCategory(){
        return ResponseEntity.ok(itemService.getCategory());
    }
    @DeleteMapping("/{uuid}/delete")
    public ResponseEntity<Response<String>> delete(@PathVariable String uuid, @RequestParam String itemId){
        return ResponseEntity.ok(itemService.xDelete(uuid,itemId));
    }
}

