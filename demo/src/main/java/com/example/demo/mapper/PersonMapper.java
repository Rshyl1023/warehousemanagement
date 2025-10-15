package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {
    List<Person> findAll();
    Person findById(String P_CODE);
    int insert(Person person);
    int delete(String P_CODE);

}