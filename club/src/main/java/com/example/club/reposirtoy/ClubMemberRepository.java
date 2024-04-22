package com.example.club.reposirtoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.club.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    // 회원 찾기 (email, social 회원여부)
    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.email = :email and m.fromSocial = :social")
    Optional<ClubMember> findByEmailAndFromSocial(String email, boolean social);
}
