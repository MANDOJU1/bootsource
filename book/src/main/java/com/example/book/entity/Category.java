package com.example.book.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "books")
@Builder
@Entity
public class Category extends BaseEntity {

    @SequenceGenerator(name = "book_catergory_seq_gen", sequenceName = "book_catergory_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_catergory_seq_gen")
    @Id
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<Book> books = new ArrayList<>();

}
