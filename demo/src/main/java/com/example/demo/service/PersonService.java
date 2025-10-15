package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dto.PersonAddDTO;
import com.example.demo.pojo.Person;

import java.util.List;

public interface PersonService extends IService<Person> {
    List<Person> getAll();
    Person getById(String id);
    boolean add(PersonAddDTO personAddDTO);
    boolean update(Person person);
    int delete(String id);
    List<Person> searchByNameKeyword(String keyword);
}