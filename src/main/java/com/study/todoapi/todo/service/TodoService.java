package com.study.todoapi.todo.service;

import com.study.todoapi.todo.dto.request.TodoCreateRequestDTO;
import com.study.todoapi.todo.dto.response.TodoDetailResponseDTO;
import com.study.todoapi.todo.dto.response.TodoListResponseDTO;
import com.study.todoapi.todo.entity.Todo;
import com.study.todoapi.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional //jpa 사용시 필수
public class TodoService {
    
    private final TodoRepository todoRepository;

    //할일 등록 기능
    public TodoListResponseDTO create(TodoCreateRequestDTO dto){
        todoRepository.save(dto.toEntity());
        log.info("새로운 할일이 저장되었습니다. 제목:{}",dto.getTitle());
        return retrieve(); //새로운 목록랜더링

    }

    //할일 목록 불러오기
    public TodoListResponseDTO retrieve(){
        List<Todo> todoList = todoRepository.findAll();
        //엔텉티 리스트를 dto리스트로 매핑
        List<TodoDetailResponseDTO> dtoList =
                todoList
                .stream()
                .map(TodoDetailResponseDTO::new)
                .collect(Collectors.toList());
        return TodoListResponseDTO.builder()
                .todos(dtoList)
                .build();
    }

    //할일 삭제
    public TodoListResponseDTO delete(String id){
        try{

            todoRepository.deleteById(id);
        }catch (Exception e){
            log.error("id가 존재하지 않아 삭제에 실패했습니다 -ID{},error{}"
            ,id,e.getMessage());
            throw  new RuntimeException("삭제에 실패했습니다!");
        }
        return retrieve();
    }


}
