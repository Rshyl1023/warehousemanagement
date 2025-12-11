package com.example.demo.service;

import com.example.demo.pojo.Person;
import com.example.demo.pojo.Resource;

import java.util.List;

public interface AuthService {
    // 1. 用户登录（明文比对）
    String login(String name, String passwordHash);

    // 2. 查询用户有权访问的资源列表
    List<Resource> getUserResources(String personCode);

    // 3. 授予/收回权限
    boolean grantPermission(String personCode, String resourceCode, boolean hasPermission);

    // 4. 添加新资源
    boolean addResource(String name, String type);
}