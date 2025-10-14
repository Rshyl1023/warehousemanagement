package com.example.demo.controller;

import com.example.demo.pojo.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public int add(@RequestBody Person person) {
        return service.add(person);
    }

    @PutMapping
    public int update(@RequestBody Person person) {
        return service.update(person);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable String id) {
        return service.delete(id);
    }
}