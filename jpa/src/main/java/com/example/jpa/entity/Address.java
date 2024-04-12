package com.example.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Embeddable // 다른 엔티티에서 포함
public class Address {
    private String city;
    private String street;
    private String zipcode;
}