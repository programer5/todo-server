package org.example.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    @PostMapping()
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        log.info("create");

        if (ObjectUtils.isEmpty(request.getTitle()))
        return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getOrder()))
            request.setOrder(0L);

        if (ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);

        TodoModel result = todoService.add(request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        log.info("READ ONE");
        TodoModel result = todoService.searchById(id);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        log.info("READ ALL");
        List<TodoModel> list = todoService.searchAll();
        List<TodoResponse> response = list.stream().map(TodoResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        log.info("UPDATE");
        TodoModel result = todoService.updateById(id, request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        log.info("DELETE");
        todoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        log.info("DELETE ALL");
        todoService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
