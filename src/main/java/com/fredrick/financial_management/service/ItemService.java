package com.fredrick.financial_management.service;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.entity.Item;
import com.fredrick.financial_management.enumeration.ItemCategory;
import com.fredrick.financial_management.request.item.CreateItemRequest;
import com.fredrick.financial_management.request.item.UpdateItemRequest;
import com.fredrick.financial_management.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {
    Response<List<Item>> findAll(String category, String type, String date, int page, int size);
    Response<List<Item>> findAll();
    Response<List<Item>> getAccountItem(String category, String type, String date, int page, int size);
    Response<List<Item>> getAccountItem(String category, String type, String date);
    Response<String> create(CreateItemRequest request);
    Response<String> xCreate(String uuid, CreateItemRequest request);
    Response<String> update(String id, UpdateItemRequest request);
    Response<String> xUpdate(String id, UpdateItemRequest request);
    Response<List<String>> getCategory();
}
