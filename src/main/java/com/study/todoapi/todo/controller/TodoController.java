package com.study.todoapi.todo.controller;

import com.study.todoapi.todo.dto.request.TodoCreateRequestDTO;
import com.study.todoapi.todo.dto.response.TodoDetailResponseDTO;
import com.study.todoapi.todo.dto.response.TodoListResponseDTO;
import com.study.todoapi.todo.entity.Todo;
import com.study.todoapi.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    //할일 등록 요청
    @PostMapping
    public ResponseEntity<?> crreateTodo(@Validated @RequestBody TodoCreateRequestDTO dto
    , BindingResult result){

        if(result.hasErrors()){
            log.warn("dto 검증에러!!:{}",result.getFieldError());
            return  ResponseEntity.badRequest().body(result.getFieldError());
        }
        try{
            TodoListResponseDTO todoListResponseDTO = todoService.create(dto);
            return ResponseEntity.ok().body(todoListResponseDTO);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()).build());

        }



    }

    //할일 목록조회요청
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        log.info("/api/todos GET!");
        //객체 자체에 키값이 생김
        TodoListResponseDTO retrieve = todoService.retrieve();
        return ResponseEntity.ok().body(retrieve);
    }

    //할일 삭제 요청처리
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable String id){
        log.info("/api/todos/{} DELETE!!",id);
        if(id==null||id.trim().equals("")){
            return ResponseEntity.badRequest().body(TodoListResponseDTO.builder()
                    .error("ID는 공백일수 없습니다!!")
                    .build());
        }
        try {
            TodoListResponseDTO dto=todoService.delete(id);
            return ResponseEntity.ok().body(dto);
        }catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()).build());
        }

    }

}
