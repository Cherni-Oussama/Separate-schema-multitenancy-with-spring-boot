package com.example.schematenancy.service;


import com.example.schematenancy.exception.ItemNotFoundException;
import com.example.schematenancy.model.ToDo;
import com.example.schematenancy.repository.TodoRepository;
import com.example.schematenancy.utils.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ToDoService {

    private final TodoRepository todoRepository;

    public List<ToDo> getAllToDo(){
        log.info("Request to get ALL TODO elements for tenant {}", TenantContext.getTenantId());
        return todoRepository.findAll();
    }

    public ToDo getToDoById(UUID toDoId){
        log.info("Request to get TODO element with id {} for tenant {}", toDoId, TenantContext.getTenantId());
        return todoRepository.findById(toDoId)
                .orElseThrow(() -> new ItemNotFoundException("ToDo not found", null, null, HttpStatus.NOT_FOUND));
    }


    public ToDo createToDo(ToDo toDo){
        log.info("Request to create TODO element for tenant {}", TenantContext.getTenantId());

        return todoRepository.save(toDo);
    }

    public void deleteToDoById(UUID toDoId){
        log.info("Request to delete TODO element with id {} for tenant {}", toDoId, TenantContext.getTenantId());
        todoRepository.deleteById(toDoId);
    }

}
