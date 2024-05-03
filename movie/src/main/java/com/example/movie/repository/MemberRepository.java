package com.example.movie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Member;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 1) findBy~
    // 2) findBy + @Query
    // 3) JPQL

    // 여러개 나올 땐 List , 한개만 나올 땐 Optional
    Optional<Member> findByEmail(String email);

    @Modifying
    @Query("update Member m set m.nickname =:nickname where m.email=:email")
    void updateNickName(String nickname, String email);

}
