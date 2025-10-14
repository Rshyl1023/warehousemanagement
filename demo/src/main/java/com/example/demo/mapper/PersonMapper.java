package com.example.demo.mapper;

import com.example.demo.pojo.Person;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonMapper {
    List<Person> findAll();
    Person findById(String P_CODE);
    int insert(Person person);
    int update(Person person);
    int delete(String P_CODE);
}