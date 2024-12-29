package com.fredrick.financial_management.dao;

import com.fredrick.financial_management.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends JpaRepository<Item,String>, JpaSpecificationExecutor<Item> {

}
