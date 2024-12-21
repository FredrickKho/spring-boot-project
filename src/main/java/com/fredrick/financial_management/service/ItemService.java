package com.fredrick.financial_management.service;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.entity.Item;
import com.fredrick.financial_management.request.item.CreateItemRequest;
import com.fredrick.financial_management.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {
    Response<List<Item>> findAll();
//    Item findById(int id);
//    void save (Item item);
//    void delete(int id);
    Response<String> create(CreateItemRequest request);
}
