package com.example.demo.service;

import com.example.demo.pojo.Person;
import java.util.List;

public interface PersonService {
    List<Person> getAll();
    Person getById(String id);
    int add(Person person);
    int update(Person person);
    int delete(String id);
}