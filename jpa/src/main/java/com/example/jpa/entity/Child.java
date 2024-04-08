package com.example.jpa.entity;

import groovy.transform.ToString;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(excludes = "parent")
@Getter
@Setter
@Entity
public class Child {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String name;

    // Child를 통해서 Parent 정보를 알 수 있음
    @ManyToOne
    private Parent parent;

}
