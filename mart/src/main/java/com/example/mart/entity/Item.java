package com.example.mart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Item {

    @SequenceGenerator(name = "mart_item_seq_gen", sequenceName = "item_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_item_seq_gen")

    @Column(name = "item_id")
    @Id
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

}

// create table item (
// price number(10,0) not null,
// stock_quantity number(10,0) not null,
// item_id number(19,0) not null,
// name varchar2(255 char),
// primary key (item_id)
// )