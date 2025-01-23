package com.fredrick.financial_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fredrick.financial_management.enumeration.ItemCategory;
import com.fredrick.financial_management.enumeration.ItemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @Column(name="id")
    private String id;
    @Column(name="name",nullable = false)
    private String name;

    @Column(name="quantity",nullable = false)
    private int quantity;
    @Column(name = "totalPrice", nullable = false)
    private Long totalPrice;
    @Column(name="category",nullable = false)
    private String category;

    @Column(name="location")
    private String location;

    @Column(name="date")
    private LocalDate date;

    @Column(name="type",nullable = false)
    private String type;

    @ManyToOne()
    @JoinColumn(name = "uuid")
    @JsonIgnore
    private Account account;

    @Column(name = "note")
    private String note;

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", name='" + name + '\'' + ", quantity=" + quantity
                + ", totalPrice=" + totalPrice + ", category=" + category + ", location='"
                + location + '\'' + ", date=" + date + ", type=" + type + ", note='" + note + '\''
                + '}';
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString()+"-item";
        }
    }

    @JsonProperty("uuid")
    public String getAccountId() {
        return account != null ? account.getUuid() : null;
    }
}
