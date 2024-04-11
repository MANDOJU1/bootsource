package com.example.todo.service;

import java.util.List;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;

public interface TodoService {

    List<TodoDto> getList();

    TodoDto create(TodoDto dto);

    TodoDto getTodo(Long id);

    List<TodoDto> getCompletedList();

    Long todoUpdate(Long id);

    void todoDelete(Long id);

    public default Todo dtoToEntity(TodoDto dto) {
        return Todo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .completed(dto.getCompleted())
                .important(dto.getImportant())
                .build();
    }

    public default TodoDto entityToDto(Todo entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .completed(entity.getCompleted())
                .important(entity.getImportant())
                .build();
    }

}