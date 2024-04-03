package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.RoleType;

@SpringBootTest
public class MemeberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Member member = Member.builder()
                    .id("user" + i)
                    .userName("user" + i)
                    .age(i)
                    .roleType(RoleType.USER)
                    .description("user" + i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void readTest() {
        System.out.println(memberRepository.findById("user1"));
        memberRepository.findAll().forEach(member -> System.out.println(member));

        memberRepository.findByUserName("user1").forEach(member -> {
            System.out.println(member);
        });
    }

    @Test
    public void updateTest() {
        Member member = memberRepository.findById("user1").get();

        // ifPresent() 방법도 있음
        member.setAge(30);
        member.setRoleType(RoleType.ADMIN);
        System.out.println(memberRepository.save(member));
    }

    @Test
    public void deleteTest() {
        Member member = memberRepository.findById("user50").get();
        memberRepository.delete(member);
    }

}
