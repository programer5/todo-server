package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoModel add(TodoRequest request) {
        TodoModel todoModel = new TodoModel();
        todoModel.setTitle(request.getTitle());
        todoModel.setOrder(request.getOrder());
        todoModel.setCompleted(request.getCompleted());
        return todoRepository.save(todoModel);
    }

    public TodoModel searchById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<TodoModel> searchAll() {
        return todoRepository.findAll();
    }

    public TodoModel updateById(Long id, TodoRequest request) {
        TodoModel todoModel = this.searchById(id);
        if (request.getTitle() != null) {
            todoModel.setTitle(request.getTitle());
        }
        if (request.getOrder() != null) {
            todoModel.setOrder(request.getOrder());
        }
        if (request.getCompleted() != null) {
            todoModel.setCompleted(request.getCompleted());
        }
        return todoRepository.save(todoModel);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }
}
