package com.example.demo;

import com.example.demo.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    public void testLoginWithNameAndPasswordHash() {
        // 测试使用用户名和password_hash登录
        String token = authService.login("张三", "123456");
        if (token != null) {
            System.out.println("登录成功，用户code: " + token);
        } else {
            System.out.println("登录失败");
        }
    }
}