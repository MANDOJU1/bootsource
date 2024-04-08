package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString(exclude = "locker")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SportsMember extends BaseEntity {
    @SequenceGenerator(name = "sports_member_seq_gen", sequenceName = "sports_member_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sports_member_seq_gen")
    @Id
    @Column(name = "member_id")
    private Long id;

    private String name;

    @OneToOne
    private Locker locker;
}