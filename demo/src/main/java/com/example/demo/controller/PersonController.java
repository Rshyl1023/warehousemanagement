package com.example.demo.controller;

import com.example.demo.dto.PersonAddDTO;
import com.example.demo.pojo.Person;
import com.example.demo.service.PersonService;
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
    public ResponseEntity<Void> add(@RequestBody PersonAddDTO personAddDTO) {

        boolean success = service.add(personAddDTO); //调用 Service 层处理 DTO 和业务逻辑

        //如果成功
        if (success) {
            //返回 201 Created 状态码，表示资源已创建
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            //返回 400 Bad Request 或其他错误状态
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