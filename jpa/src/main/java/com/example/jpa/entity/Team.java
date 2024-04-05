package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = { "members" }) // ToString 생성 시 클래스 내 모든 property 가 기준임
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Team {

    @Column(name = "team_id")
    @Id
    private String id;

    @Column(name = "team_name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER) // 다대일 중 다에 해당하는 many인(주된)것을 지칭해줘야함
    // N : 1 일때 N에 해당하는 것을 List로 둠
    private List<TeamMember> members = new ArrayList<>();

}
