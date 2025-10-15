package com.example.demo.controller;

import com.example.demo.dto.PersonAddDTO;
import com.example.demo.pojo.Person;
import com.example.demo.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping
    public List<Person> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    // 1. 接收 PersonAddDTO，并用 @Valid 校验
    // 2. 约定返回 ResponseEntity<Boolean> 或 ResponseEntity<Void>，这里返回 ResponseEntity<Void>
    //    表示操作结果，状态码 201 表示资源创建成功，更符合 RESTful 规范。
    public ResponseEntity<Void> add(PersonAddDTO personAddDTO) {

        boolean success = service.add(personAddDTO); // 调用 Service 层处理 DTO 和业务逻辑

        if (success) {
            // 返回 201 Created 状态码，表示资源已创建
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            // 返回 400 Bad Request 或其他错误状态
            // 实际项目中这里可能抛出异常由全局处理
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public boolean update(@RequestBody Person person) {
        return service.update(person);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable String id) {
        return service.delete(id);
    }

    @GetMapping("/search")
    public List<Person> searchByName(@RequestParam(required = false) String keyword) {
        return service.searchByNameKeyword(keyword);
    }
}