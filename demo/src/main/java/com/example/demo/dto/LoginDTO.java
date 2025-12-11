package com.example.demo.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String name;
    private String passwordHash;
}