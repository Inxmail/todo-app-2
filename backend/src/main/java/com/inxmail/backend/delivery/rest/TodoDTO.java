package com.inxmail.backend.delivery.rest;

import com.inxmail.backend.domain.model.Todo;


public record TodoDTO(long id, String title, boolean completed) {
    public static TodoDTO convert( Todo todo ) {
        return new TodoDTO( todo.getId(), todo.getTitle(), todo.isCompleted() );
    }
}
