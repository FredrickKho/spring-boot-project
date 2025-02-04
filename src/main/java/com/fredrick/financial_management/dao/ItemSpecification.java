package com.fredrick.financial_management.dao;

import com.fredrick.financial_management.entity.Item;
import com.fredrick.financial_management.enumeration.ItemCategory;
import com.fredrick.financial_management.enumeration.ItemType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ItemSpecification {
    public static Specification<Item> filterByCategory(String itemCategory) {
        return (root, query, criteriaBuilder) -> {
            if (itemCategory != null) {
                return criteriaBuilder.equal(root.get("category"), itemCategory);
            }
            return null;
        };
    }

    public static Specification<Item> filterByType(String type) {
        return (root, query, criteriaBuilder) -> {
            if (type != null) {
                return criteriaBuilder.equal(root.get("type"), type);
            }
            return null;
        };
    }

    public static Specification<Item> filterByStartDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("date"), date);
            }
            return null;
        };
    }
    public static Specification<Item> filterByEndDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("date"), date);
            }
            return null;
        };
    }
    public static Specification<Item> filterByDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date != null) {
                return criteriaBuilder.equal(root.get("date"), date);
            }
            return null;
        };
    }
    public static Specification<Item> filterByUUID(String uuid) {
        return (root, query, criteriaBuilder) -> {
            if (uuid != null) {
                // Access the 'account' relationship and use the 'uuid' field
                return criteriaBuilder.equal(root.get("account").get("uuid"), uuid);
            }
            return null;
        };
    }
    public static Specification<Item> filter(String category, String type, LocalDate date) {
        return Specification.where(filterByCategory(category))
                .and(filterByType(type))
                .and(filterByDate(date));
    }
    public static Specification<Item> filter(String category, String type, LocalDate startDate, LocalDate endDate, String uuid) {
        return Specification.where(filterByCategory(category))
                .and(filterByType(type))
                .and(filterByStartDate(startDate))
                .and(filterByEndDate(endDate))
                .and(filterByUUID(uuid));
    }
}
