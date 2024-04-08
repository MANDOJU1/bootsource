package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import groovy.transform.ToString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(excludes = "childList")
@Getter
@Setter
@Entity
public class Parent {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String name;

    // casecade : 영속상태 전이
    // 부모가 영속 상태 시 자식도 같이 영속상태로 감
    // orphanRemoval : 부모하고 연관관계가 끊어진 경우(고아객체) 자동으로 삭제
    // OneToMany는 List
    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true) // @OneToMany
                                                                                                              // :
                                                                                                              // FetchType.LAZY
    private List<Child> childList = new ArrayList<>();

}
