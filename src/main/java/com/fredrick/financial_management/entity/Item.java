package com.fredrick.financial_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fredrick.financial_management.enumeration.ItemCategory;
import com.fredrick.financial_management.enumeration.ItemType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @JsonIgnore
    private int id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="quantity",nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name="category",nullable = false)
    private ItemCategory category;

    @Column(name="location")
    private String location;

    @Column(name="date")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name="type",nullable = false)
    private ItemType type;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "account.uuid")
    private Account account;

    @Column(name = "note")
    private String note;
    public Item(int id,
            String name,
            int quantity,
            ItemCategory category,
            String location,
            Date date,
            ItemType type,
            String note
            ) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.location = location;
        this.date = date;
        this.type = type;
        this.note = note;
    }

    public Item(String name,
            int quantity,
            ItemCategory category,
            String location,
            Date date,
            ItemType type,
            String note
            ) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.location = location;
        this.date = date;
        this.type = type;
        this.note = note;
    }
}
