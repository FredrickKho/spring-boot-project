package com.fredrick.financial_management.service;

import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();
    Item findById(int id);
    void save (Item item);
    void deleteById(int id);
}
