package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Parent;

@SpringBootTest
public class ParentRepositoryTest {

    @Autowired
    private ParentRepositroy parentRepositroy;

    @Autowired
    private ChildRepository childRepository;

    @Test
    public void insertTest() {
        // 부모 한명에 자식 2명
        Parent parent = Parent.builder().name("parent1").build();
        parentRepositroy.save(parent);

        Child child = Child.builder().name("child1").parent(parent).build();
        childRepository.save(child);
        Child child2 = Child.builder().name("child2").parent(parent).build();
        childRepository.save(child2);
    }

    @Test
    public void insertCasecadeTest() {
        // 부모 한명에 자식 2명
        Parent parent = Parent.builder().name("parent2").build();

        // 이 방식은 Child는 insert가 되지 않음
        // 해결방안 Parent에 cascade = CascadeType.ALL 넣음
        Child child = Child.builder().name("child1").parent(parent).build();
        parent.getChildList().add(child);
        Child child2 = Child.builder().name("child2").parent(parent).build();
        parent.getChildList().add(child2);

        parentRepositroy.save(parent);

        // InvalidDataAccessApiUsageException 오류남
        // childRepository.save(child);
        // childRepository.save(child2);
    }

    @Test
    public void deleteTest() {
        // 부모가 자식을 가지고 있는 경우 삭제 시 자식의 부모 아이디 변경 후 부모 삭제
        Parent parent = Parent.builder().id(1L).build();

        // 부모를 NULL 로 지정
        // Child child = Child.builder().id(1L).parent(null).build();
        // Child child2 = Child.builder().id(2L).parent(null).build();

        Child child = Child.builder().id(1L).build();
        childRepository.delete(child);
        Child child2 = Child.builder().id(2L).build();
        childRepository.delete(child2);

        parentRepositroy.delete(parent);

    }

    @Test
    public void deleteCasecadeTest() {
        // 부모 한명에 자식 2명
        Parent parent = Parent.builder().id(52L).build();

        Child child = Child.builder().id(102L).build();
        parent.getChildList().add(child);
        Child child2 = Child.builder().id(103L).build();
        parent.getChildList().add(child2);

        parentRepositroy.delete(parent);
    }

    // @Transactional
    @Test
    public void deleteOrphanTest1() {
        // 기본 자식 여부
        Parent parent = parentRepositroy.findById(102L).get();
        // FetchType 이 LAZY 인 경우 오류 발생
        System.out.println("기존 자식 " + parent.getChildList());

        // LazyInitializationException 오류
        // 해결방안 : @Transactional 추가 !
        parent.getChildList().remove(0); // 인덱스 제거 => childList 에서 제거 => 엔티티 제거

        parentRepositroy.save(parent);
    }

}
