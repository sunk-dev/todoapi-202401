package com.study.todoapi.todo.service;

import com.study.todoapi.todo.dto.request.TodoCreateRequestDTO;
import com.study.todoapi.todo.entity.Todo;
import com.study.todoapi.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional //jpa 사용시 필수
public class TodoService {
    
    private final TodoRepository todoRepository;

    //할일 등록 기능
    public void create(TodoCreateRequestDTO dto){
        todoRepository.save(dto.toEntity());
        log.info("새로운 할일이 저장되었습니다. 제목:{}",dto.getTitle());

    }
}
