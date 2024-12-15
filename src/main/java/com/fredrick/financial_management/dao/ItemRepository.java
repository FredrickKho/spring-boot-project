package com.fredrick.financial_management.dao;

import com.fredrick.financial_management.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Integer> {

}
