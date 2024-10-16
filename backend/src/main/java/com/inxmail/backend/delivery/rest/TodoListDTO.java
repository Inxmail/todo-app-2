package com.inxmail.backend.delivery.rest;

import java.util.List;
import java.util.stream.Collectors;

import com.inxmail.backend.domain.model.TodoList;


public record TodoListDTO(long id, String name, boolean completed, List<TodoDTO> todos) {
    public static TodoListDTO konvertiere( TodoList todoList ) {
        return new TodoListDTO( todoList.getId(), todoList.getName(), todoList.isCompleted(),
                todoList.getTodos().stream().map( TodoDTO::convert ).collect( Collectors.toList() ) );
    }
}
