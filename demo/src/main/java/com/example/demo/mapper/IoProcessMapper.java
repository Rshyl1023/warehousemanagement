package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.Map;

@Mapper
public interface IoProcessMapper {
    void callIoProcess(Map<String, Object> params);
}