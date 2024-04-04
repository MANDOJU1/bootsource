package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.TeamMember;

// <Entity, Entity에서 pk로 쓰이는 컬럼 타입>
public interface TeamMemberRepository extends JpaRepository<TeamMember, String> {

}
