package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.Todo;

@SpringBootTest
public class TodoRepositoryTest {
    // DAO == TodoRepository
    // service 에서 호출하는 메소드 테스트z

    @Autowired
    private TodoRepository todoRepository;

    // todo 삽입
    @Test
    public void insertTest() {
        // 내가 한 방법
        // Todo todo1 =
        // todoRepository.save(Todo.builder().completed(false).important(false).title("운동").build());
        // Todo todo2 =
        // todoRepository.save(Todo.builder().completed(false).important(true).title("식사").build());
        // Todo todo3 =
        // todoRepository.save(Todo.builder().completed(false).important(false).title("잠").build());

        // 강사님이 한 방법
        // IntStream.rangeClosed(1, 10).forEach(i -> {
        // Todo todo = Todo.builder().title("강아지 목욕" +
        // i).completed(false).important(true).build();
        // todoRepository.save(todo);
        // });

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Todo todo = Todo.builder().title("강아지 목욕" + i).build();
            todoRepository.save(todo);
        });
    }

    // todo 전체 목록 조회
    @Test
    public void todoList() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    // todo 전체 목록 조회
    @Test
    public void todoList() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    // todo 한개만 조회
    @Test
    public void todoRow() {
        System.out.println(todoRepository.findById(3L));
    }
}
