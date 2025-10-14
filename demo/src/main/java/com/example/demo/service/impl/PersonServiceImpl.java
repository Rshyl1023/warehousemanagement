package com.example.demo.service.impl;

import com.example.demo.mapper.PersonMapper;
import com.example.demo.pojo.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonMapper mapper;

    @Override
    public List<Person> getAll() {
        return mapper.findAll();
    }

    @Override
    public Person getById(String id) {
        return mapper.findById(id);
    }

    @Override
    public int add(Person person) {
        return mapper.insert(person);
    }

    @Override
    public int update(Person person) {
        return mapper.update(person);
    }

    @Override
    public int delete(String id) {
        return mapper.delete(id);
    }
}