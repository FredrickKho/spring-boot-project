package com.fredrick.financial_management.controller;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.entity.Item;
import com.fredrick.financial_management.request.account.UpdateAccountRequest;
import com.fredrick.financial_management.request.item.CreateItemRequest;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/item")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<Item>>> getItem(){
        return ResponseEntity.ok(itemService.findAll());
    }

//    @PatchMapping(path = "/{uuid}/update")
//    public ResponseEntity<Response<String>> updateItem(@PathVariable String itemId, @RequestBody UpdateAccountRequest request){
//        return ResponseEntity.ok(itemService.save(uuid,request));
//    }
//
//    @DeleteMapping("/{uuid}/delete")
//    public ResponseEntity<Response<String>> deleteItem(@PathVariable String itemId){
//        return ResponseEntity.ok(itemService.delete(itemId));
//    }

    @PostMapping("/create")
    public ResponseEntity<Response<String>> createItem(@RequestBody CreateItemRequest request) {
        return ResponseEntity.ok(itemService.create(request));
    }
}
