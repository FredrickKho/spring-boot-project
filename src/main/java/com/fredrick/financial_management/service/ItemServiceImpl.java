package com.fredrick.financial_management.service;

import com.fredrick.financial_management.dao.AccountRepository;
import com.fredrick.financial_management.dao.ItemRepository;
import com.fredrick.financial_management.dao.ItemSpecification;
import com.fredrick.financial_management.entity.Account;
import com.fredrick.financial_management.entity.Item;
import com.fredrick.financial_management.enumeration.ItemCategory;
import com.fredrick.financial_management.enumeration.ItemType;
import com.fredrick.financial_management.exception.crud.DataNotFoundException;
import com.fredrick.financial_management.exception.crud.InvalidRequestException;
import com.fredrick.financial_management.request.item.CreateItemRequest;
import com.fredrick.financial_management.request.item.UpdateItemRequest;
import com.fredrick.financial_management.response.Pagination;
import com.fredrick.financial_management.response.Response;
import com.fredrick.financial_management.util.ValidatorUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AccountRepository accountRepository;
    Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
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
    public Response<List<Item>> getAccountItem(String category, String type, String startDate, String endDate){
        Account account =
                (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uuid = account.getUuid();
        ItemCategory itemCategory = ItemCategory.getCategoryByName(category);
        ItemType itemType = ItemType.getTypeByName(type);
        LocalDate filterStartDate = null, filterEndDate = null;
        if(startDate!= null && ValidatorUtil.isValidLocalDate(startDate)){
            filterStartDate = LocalDate.parse(startDate);
        }
        if(endDate!= null && ValidatorUtil.isValidLocalDate(endDate)){
            filterEndDate = LocalDate.parse(endDate);
        }
        Specification<Item> filters = ItemSpecification.filter(
                itemCategory != null ? itemCategory.getName() : null,
                itemType != null ? itemType.getName() : null,
                filterStartDate,
                filterEndDate,
                uuid
        );
        List<Item> getItem = itemRepository.findAll(filters);
        return Response.<List<Item>>builder()
                .data(getItem)
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .pagination(null)
                .build();
    }
    @Override
    public Response<List<Item>> getAccountItem(String category,
            String type,
            String startDate,
            String endDate,
            int page,
            int size) {
        Account account =
                (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uuid = account.getUuid();
        ItemCategory itemCategory = category != null ? ItemCategory.getCategoryByName(category) : null;
        ItemType itemType = type != null ? ItemType.getTypeByName(type) : null;
        LocalDate filterStartDate = null, filterEndDate = null;
        if(startDate!= null && ValidatorUtil.isValidLocalDate(startDate)){
            filterStartDate = LocalDate.parse(startDate);
        }
        if(endDate!= null && ValidatorUtil.isValidLocalDate(endDate)){
            filterEndDate = LocalDate.parse(endDate);
        }
        Specification<Item> filters = ItemSpecification.filter(
                itemCategory != null ? itemCategory.getName() : null,
                itemType != null ? itemType.getName() : null,
                filterStartDate,
                filterEndDate,
                uuid
        );
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Item> itemPage = itemRepository.findAll(filters,pageable);
        //        logger.debug(itemPage.toString());
        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .totalPage(itemPage.getTotalPages())
                .totalSize((int) itemPage.getTotalElements())
                .build();
        return Response.<List<Item>>builder()
                .data(itemPage.getContent())
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .pagination(pagination)
                .build();
    }

    @Override
    public Response<List<Item>> findAll(String category, String type, String date, int page, int size) {
        ItemCategory itemCategory = category != null ? ItemCategory.getCategoryByName(category) : null;
        ItemType itemType = type != null ? ItemType.getTypeByName(type) : null;
        LocalDate localDate = null;
        if(date!= null && ValidatorUtil.isValidLocalDate(date)){
            localDate = LocalDate.parse(date);
        }
        Specification<Item> filters = ItemSpecification.filter(
                itemCategory != null ? itemCategory.getName() : null,
                itemType != null ? itemType.getName() : null,
                localDate
        );        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Item> itemPage = itemRepository.findAll(filters,pageable);
//        logger.debug(itemPage.toString());
        Pagination pagination = Pagination.builder()
                .page(page)
                .size(size)
                .totalPage(itemPage.getTotalPages())
                .totalSize((int) itemPage.getTotalElements())
                .build();
        return Response.<List<Item>>builder()
                .data(itemPage.getContent())
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .pagination(pagination)
                .build();
    }
    @Override
    @Transactional
    public Response<String> create(CreateItemRequest request) {
        Account account =
                (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> errMessage = new ArrayList<>();
        ItemCategory category = null;
        ItemType type = null;
        LocalDate date = null;
        System.out.println("CreateItemRequest : "+request);
        System.out.println("Account : "+account);
        if (request.getName() == null) {
            errMessage.add("Name must not empty");
        }
        if (request.getQuantity() == null) {
            errMessage.add("Quantity must not empty");
        } else {
            if (!request.getQuantity().matches("[0-9]+")) {
                errMessage.add("Quantity must a number");
            }
        }
        if (request.getTotalPrice() == null) {
            errMessage.add("Total price must not empty");
        } else {
            if (!request.getTotalPrice().matches("[0-9]+")) {
                errMessage.add("Total price must a number");
            }
        }
        if (request.getCategory() == null) {
            errMessage.add("Category must not empty");
        } else {
            category = ItemCategory.getCategoryByName(request.getCategory());
            if (category == null){
                errMessage.add("Invalid category or there's no such value for category type");
            }
        }
        if (request.getType() == null) {
            errMessage.add("Item type must not empty");
        } else {
            type = ItemType.getTypeByName(request.getType());
            if (type == null){
                errMessage.add("Invalid item type or there's no such value for item type");
            }
        }
        if(request.getDate() == null){
            errMessage.add("Date must not empty");
        } else {
            if (!ValidatorUtil.isValidLocalDate(request.getDate()))
                errMessage.add("Date format is invalid (must be YYYY/MM/DD and valid day and month)");
            else
                date = LocalDate.parse(request.getDate());
        }

        if (!errMessage.isEmpty()) {
            throw new InvalidRequestException(errMessage);
        }
        var item = Item.builder()
                .name(request.getName())
                .quantity(Integer.parseInt(request.getQuantity()))
                .totalPrice(Long.parseLong(request.getTotalPrice()))
                .category(category.getName())
                .location(request.getLocation())
                .date(date)
                .type(type.getName())
                .note(request.getNote())
                .build();
        item.setAccount(entityManager.merge(account));
//        System.out.println("Account + "+account);
//        System.out.println("Account (After Merge) + "+entityManager.merge(account));
        itemRepository.save(item);
        return Response.<String>builder()
                .data("")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    public Response<String> xCreate(String uuid, CreateItemRequest request) {
        Account account;
        try {
            account = accountRepository.findByUuid(uuid).get(0);
        } catch (Exception e){
          throw new DataNotFoundException("Account doesn't exist");
        }
        List<String> errMessage = new ArrayList<>();
        ItemCategory category = null;
        ItemType type = null;
        LocalDate date = null;
        System.out.println("CreateItemRequest : "+request);
        if (request.getName() == null) {
            errMessage.add("Name must not empty");
        }
        if (request.getQuantity() == null) {
            errMessage.add("Quantity must not empty");
        } else {
            if (!request.getQuantity().matches("[0-9]+")) {
                errMessage.add("Quantity must a number");
            }
        }
        if (request.getTotalPrice() == null) {
            errMessage.add("Total price must not empty");
        } else {
            if (!request.getTotalPrice().matches("[0-9]+")) {
                errMessage.add("Total price must a number");
            }
        }
        if (request.getCategory() == null) {
            errMessage.add("Category must not empty");
        } else {
            category = ItemCategory.getCategoryByName(request.getCategory());
            if (category == null){
                errMessage.add("Invalid category or there's no such value for category type");
            }
        }
        if (request.getType() == null) {
            errMessage.add("Item type must not empty");
        } else {
            type = ItemType.getTypeByName(request.getType());
            if (type == null){
                errMessage.add("Invalid item type or there's no such value for item type");
            }
        }
        if(request.getDate() == null){
            errMessage.add("Date must not empty");
        } else {
            if (!ValidatorUtil.isValidLocalDate(request.getDate()))
                errMessage.add("Date format is invalid (must be YYYY/MM/DD and valid day and month)");
            else
                date = LocalDate.parse(request.getDate());
        }
        if (!errMessage.isEmpty()) {
            throw new InvalidRequestException(errMessage);
        }
        var item = Item.builder()
                .name(request.getName())
                .quantity(Integer.parseInt(request.getQuantity()))
                .totalPrice(Long.parseLong(request.getTotalPrice()))
                .category(category.getName())
                .location(request.getLocation())
                .date(date)
                .type(type.getName())
                .note(request.getNote())
                .build();
        item.setAccount(account);
        itemRepository.save(item);
        return Response.<String>builder()
                .data("")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    public Response<String> xUpdate(String id, UpdateItemRequest request) {
        List<String> errMessage = new ArrayList<>();
        Optional<Item> findItem = itemRepository.findById(id);
        Item item;
        if(findItem.isPresent()){
            item = findItem.get();
        }else{
            return Response.<String>builder()
                    .data("UPDATE FAILED")
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK.getReasonPhrase())
                    .timestamp(System.currentTimeMillis())
                    .build();
        }
        System.out.println("UpdateItemRequest : "+request);
        if (request.getName() != null) {
            item.setName(request.getName());
        }
        if (request.getQuantity() != null) {
            try{
                item.setQuantity(Integer.parseInt(request.getQuantity()));
            }catch (Exception e){
                errMessage.add("Quantity must be a number");
            }
        }
        if (request.getTotalPrice() != null) {
            try{
                item.setTotalPrice(Long.parseLong(request.getTotalPrice()));
            }catch (Exception e){
                errMessage.add("Total price must be a number");
            }
        }
        if (request.getCategory() != null) {
            if(ItemCategory.getCategoryByName(request.getCategory()) != null){
                item.setCategory(request.getCategory());
            } else {
                errMessage.add("Invalid category item or there's no such value for category");
            }
        }
        if (request.getLocation() != null) {
            item.setLocation(request.getLocation());
        }
        if (request.getType() != null) {
            if(ItemType.getTypeByName(request.getType()) != null){
                item.setType(request.getType());
            } else {
                errMessage.add("Invalid item type or there's no such value for item type");
            }
        }
        if (request.getNote() != null) {
            item.setNote(request.getNote());
        }
        if (request.getDate() != null) {
            if (!ValidatorUtil.isValidLocalDate(request.getDate()))
                errMessage.add("Date format is invalid (must be YYYY/MM/DD and valid day and month)");
            else
                item.setDate(LocalDate.parse(request.getDate()));
        }
        if (!errMessage.isEmpty()) {
            throw new InvalidRequestException(errMessage);
        }
        itemRepository.save(item);
        return Response.<String>builder()
                .data("UPDATE SUCCESS")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    public Response<List<String>> getCategory() {
        List<String> categories = Arrays.stream(ItemCategory.values()).map(ItemCategory::getName).collect(Collectors.toList());
        return Response.<List<String>>builder()
                .data(categories)
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    @Transactional
    public Response<String> delete(String id) {
        Account account = entityManager.merge((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            if(!Objects.equals(item.get().getAccountId(), account.getUuid())){
                return Response.<String>builder()
                        .data("DELETE FAILED, ITEM IS NOT BELONG TO THIS ACCOUNT")
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK.getReasonPhrase())
                        .timestamp(System.currentTimeMillis())
                        .build();
            }else{
//                logger.info("====================================="+item.get().toString());
                try {
                    account.getItems().remove(item.get());
                    accountRepository.save(account);
                    return Response.<String>builder()
                            .data("Delete Success")
                            .code(HttpStatus.OK.value())
                            .status(HttpStatus.OK.getReasonPhrase())
                            .timestamp(System.currentTimeMillis())
                            .build();
                } catch (Exception e) {
                    e.printStackTrace(); // Log the exception
                    throw new RuntimeException("Error deleting the item", e);
                }
            }
        }else{
            throw new DataNotFoundException("Item not found");
        }
    }

    @Override
    @Transactional
    public Response<String> xDelete(String uuid, String id) {
        Account account = accountRepository.findByUuid(uuid).get(0);
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            if(!Objects.equals(item.get().getAccountId(), account.getUuid())){
                return Response.<String>builder()
                        .data("DELETE FAILED, ITEM IS NOT BELONG TO THIS ACCOUNT")
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK.getReasonPhrase())
                        .timestamp(System.currentTimeMillis())
                        .build();
            }else{
                //                logger.info("====================================="+item.get().toString());
                try {
                    account.getItems().remove(item.get());
                    accountRepository.save(account);
                    return Response.<String>builder()
                            .data("Delete Success")
                            .code(HttpStatus.OK.value())
                            .status(HttpStatus.OK.getReasonPhrase())
                            .timestamp(System.currentTimeMillis())
                            .build();
                } catch (Exception e) {
                    e.printStackTrace(); // Log the exception
                    throw new RuntimeException("Error deleting the item", e);
                }
            }
        }else{
            throw new DataNotFoundException("Item not found");
        }
    }
    @Override
    public Response<String> update(String id, UpdateItemRequest request) {
        Account account =
                (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug(String.valueOf(account));
        System.out.println(account);
        if(account.getItems().stream().anyMatch(item -> item.getId().equals(id))){
            return xUpdate(id,request);
        } else {
            logger.error("This account does not own this item");
            return Response.<String>builder()
                    .data("UPDATE FAILED")
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK.getReasonPhrase())
                    .timestamp(System.currentTimeMillis())
                    .build();
        }
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
