package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Member;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    // 이름이 홍길동인 사람을 찾으려면
    // select * from where name = "홍길동"
    List<Member> findByUserName(String userName);
}
