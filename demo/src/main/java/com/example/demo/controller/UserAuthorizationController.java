package com.example.demo.controller;

import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authz")
public class UserAuthorizationController {
    @Autowired
    private AuthService authService;

    // 授予权限
    @PostMapping("/grant")
    public boolean grant(@RequestParam String userCode,
                         @RequestParam String resourceCode,
                         @RequestParam(defaultValue = "true") boolean allow) {
        return authService.grantPermission(userCode, resourceCode, allow);
    }

    // 添加资源
    @PostMapping("/resource")
    public boolean addResource(@RequestParam String name,
                               @RequestParam(required = false) String type) {
        return authService.addResource(name, type);
    }
}
