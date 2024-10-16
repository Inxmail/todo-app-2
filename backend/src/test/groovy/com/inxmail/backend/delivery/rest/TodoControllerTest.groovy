package com.inxmail.backend.delivery.rest


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

import com.inxmail.backend.domain.model.Todo
import com.inxmail.backend.domain.model.TodoList
import com.inxmail.backend.domain.model.TodoListRepository
import com.inxmail.backend.domain.model.TodoRepository


@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles( "test" )
@Sql( "/data.sql" )
class TodoControllerTest extends Specification {
    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    TodoRepository todoRepository

    @Autowired
    TodoListRepository todoListRepository

    void "test toggling the completed state of a todo"() {
        given:
            Todo todo = todoRepository.findById( 1 ).get();

        expect:
            !todo.isCompleted()

        when:
            def response = restTemplate.postForEntity( "/todos/${todo.getId()}/toggle", null, TodoDTO.class )

        then:
            response.statusCode == HttpStatus.OK
            response.body == new TodoDTO( todo.getId(), todo.getTitle(), !todo.isCompleted() )

        when:
            response = restTemplate.postForEntity( "/todos/${todo.getId()}/toggle", null, TodoDTO.class )

        then:
            response.statusCode == HttpStatus.OK
            response.body == TodoDTO.convert( todo )
    }

    void "test setting all todos of a list to completed also completes the list"() {
        given:
            TodoList todoList1 = todoListRepository.findById( 1 ).get();
            List<Todo> todos = todoList1.getTodos()

        expect:
            !todoList1.completed
            todos.forEach {assert !it.isCompleted()}

        when:
            todos.forEach {restTemplate.postForEntity( "/todos/${it.getId()}/toggle", null, TodoDTO.class )}

        and:
            todoList1 = todoListRepository.findById( 1 ).get();
            todos = todoList1.getTodos()

        then:
            todos.forEach {assert it.isCompleted()}
            todoList1.completed
    }
}
