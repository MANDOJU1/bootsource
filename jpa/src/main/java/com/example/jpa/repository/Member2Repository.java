package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.jpa.entity.Member2;
import com.example.jpa.entity.Team2;

public interface Member2Repository extends JpaRepository<Member2, Long>, QuerydslPredicateExecutor<Member2> {

    // jpql 사용 시
    // 1. entity 타입 결과 => List<Entity명>
    // 2. 개별 타입 결과 => List<Object[]>

    @Query("SELECT m FROM Member2 m ")
    List<Member2> findByMembers(Sort Sort);

    // ConverterNotFoundException
    // List<Member2> ~ 하면 오류가 남
    // m.userName, m.age => String, int 기 때문에 Object[] 배열로 받아줘야함
    @Query("SELECT m.userName, m.age FROM Member2 m ")
    List<Object[]> findByMembers2();

    // 특정 나이보다 많은 회원 조회
    // 두가지 방법
    // @Query("SELECT m FROM Member2 m WHERE m.age > :age ")
    @Query("SELECT m FROM Member2 m WHERE m.age > ?1")
    List<Member2> findByAgeList(int age);

    // 특정 팀의 회원 조회
    @Query("SELECT m FROM Member2 m WHERE m.team2 = ?1")
    List<Member2> findByTeamEqual(Team2 team2);

    @Query("SELECT m FROM Member2 m WHERE m.team2.id = ?1")
    List<Member2> findByIdEqual(Long id);

    // 집계함수
    @Query("SELECT COUNT(m), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age) FROM Member2 m")
    List<Object[]> aggregate();

    // join jpql_team t1_0 on t1_0.team_id=m1_0.team2_team_id => DB에서 사용하는 것처럼 바꿔줌
    // 내부조인
    @Query("SELECT m FROM Member2 m JOIN m.team2 t WHERE t.name = :teamName")
    List<Member2> findByTeamMember(String teamName);

    @Query("SELECT m,t FROM Member2 m JOIN m.team2 t WHERE t.name = :teamName")
    List<Object[]> findByTeamMember2(String teamName);

    // 외부조인
    @Query("SELECT m,t FROM Member2 m LEFT JOIN m.team2 t ON t.name = :teamName")
    List<Object[]> findByTeamMember3(String teamName);
}
