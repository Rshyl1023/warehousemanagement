package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.pojo.Resource;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserLoginController {
    @Autowired
    private AuthService authService;

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        // 正则校验（用户名：字母数字下划线汉字，长度1-50；密码：6-20位）
        if (!dto.getName().matches("^[a-zA-Z0-9_\\u4e00-\\u9fa5]{1,50}$") ||
                !dto.getPasswordHash().matches("^.{6,20}$")) {
            return ResponseEntity.badRequest().body("用户名或密码格式错误");
        }

        String token = authService.login(dto.getName(), dto.getPasswordHash());
        if (token == null) {
            return ResponseEntity.status(401).body("用户名或密码错误");
        }
        return ResponseEntity.ok(Map.of("userCode", token));
    }

    // 获取用户菜单
    @GetMapping("/menu")
    public List<Resource> getMenu(@RequestParam String userCode) {
        return authService.getUserResources(userCode);
    }
}