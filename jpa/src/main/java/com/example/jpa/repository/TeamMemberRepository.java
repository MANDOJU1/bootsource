package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.TeamMember;

// <Entity, Entity에서 pk로 쓰이는 컬럼 타입>
public interface TeamMemberRepository extends JpaRepository<TeamMember, String> {

    // sql 아님(객체를 기준으로 작성해야 함)
    @Query("select m, t from TeamMember m join m.team t where t.name = ?1")
    public List<TeamMember> findByMemberEqualTeam(String teamName);
}
