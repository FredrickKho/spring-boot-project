package com.fredrick.financial_management.service;

import com.fredrick.financial_management.dao.ItemRepository;
import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.entity.Item;
import com.fredrick.financial_management.exception.crud.DataIsRequiredException;
import com.fredrick.financial_management.request.item.CreateItemRequest;
import com.fredrick.financial_management.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{
    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Response<List<Item>> findAll() {
        Response<List<Item>> item = Response.<List<Item>>builder()
                .data(itemRepository.findAll())
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
        return item;
    }

    @Override
    public Response<String> create (CreateItemRequest request){
        System.out.println(request);
        List<String> errMessage = new ArrayList<>();
        if(request.getName() == null){
            errMessage.add("Name must not empty");
        }
        if(request.getCategory() == null){
            errMessage.add("Category must not empty");
        }
        if(request.getItemType() == null){
            errMessage.add("Item type must not empty");
        }
        if(!errMessage.isEmpty()){
            throw new DataIsRequiredException(errMessage.toString());
        }
        return null;
    }

//    @Override
//    public void save(Item item) {
//        itemRepository.save(item);
//    }
//
//    @Override
//    public void deleteById(int id) {
//        itemRepository.deleteById(id);
//    }
}
