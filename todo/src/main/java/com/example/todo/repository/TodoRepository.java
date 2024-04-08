package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Todo;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // completed 컬럼 값에 따라 조회
    List<Todo> findByCompleted(boolean completed);

    // important 컬럼 값에 따라 조회
    List<Todo> findByImportant(boolean important);
}
