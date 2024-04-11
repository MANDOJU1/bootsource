package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.Todo;

@SpringBootTest
public class TodoRepositoryTest {
    // DAO == TodoRepository
    // service 에서 호출하는 메소드 테스트

    @Autowired
    private TodoRepository todoRepository;

    // todo 삽입
    @Test
    public void todoInsert() {

        // insert into todotbl
        // (completed,created_date,important,last_modified_date,title,todo_id) values
        // (?,?,?,?,?,?)

        // IntStream.rangeClosed(1, 10).forEach(i -> {
        // Todo todo = Todo.builder().title("강아지 목욕 " +
        // i).completed(false).important(true).build();
        // todoRepository.save(todo);
        // });

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Todo todo = Todo.builder().title("강아지 목욕 " + i).build();
            todoRepository.save(todo);
        });
    }

    // todo 전체 목록 조회
    @Test
    public void todoList() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    // todo 완료 목록 조회
    @Test
    public void todoCompletedList() {
        todoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));
    }

    // todo 중요 목록 조회
    @Test
    public void todoImportentList() {
        todoRepository.findByImportant(true).forEach(todo -> System.out.println(todo));
    }

    @Test
    public void todoRow() {
        System.out.println(todoRepository.findById(3L));
    }

    // todo 수정
    // 제목/ 완료 / 중요

    @Test
    public void todoUpdate() {
        Todo entity = todoRepository.findById(2L).get();
        entity.setCompleted(true);
        todoRepository.save(entity);

        entity = todoRepository.findById(3L).get();
        entity.setTitle("자바 공부");
        todoRepository.save(entity);

        entity = todoRepository.findById(5L).get();
        entity.setTitle("스프링 공부");
        entity.setImportant(true);
        entity.setCompleted(true);
        todoRepository.save(entity);
    }

    // todo 삭제
    @Test
    public void todoDelete() {
        todoRepository.deleteById(10L);
    }
}
