package com.example.schematenancy.controller;


import com.example.schematenancy.model.ToDo;
import com.example.schematenancy.service.ToDoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
@Tag(name = "ToDo")
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping("/all")
    public ResponseEntity<List<ToDo>> getAllToDo(){
        return ResponseEntity.ok(toDoService.getAllToDo());
    }

    @GetMapping("/{elemId}")
    public ResponseEntity<ToDo> getToDoElemById(@PathVariable("elemId") UUID toDoElemId){
        return ResponseEntity.ok(toDoService.getToDoById(toDoElemId));
    }

    @PostMapping("/")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDoElem){
        return ResponseEntity.ok(toDoService.createToDo(toDoElem));
    }

    @DeleteMapping("/{elemId}")
    public ResponseEntity<Void> deleteToDoById(@PathVariable("elemId") UUID toDoElemId){
        toDoService.deleteToDoById(toDoElemId);
        return ResponseEntity.noContent().build();
    }

}
