package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@TableName("person")
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    // 使用 @TableId 标记主键，IdType.ASSIGN_ID 表示使用 Mybatis-Plus 的雪花算法（分布式）生成ID
    @TableId(value = "code", type = IdType.ASSIGN_ID)
    private String code;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String idCard;
    private String nativePlace;
    private String address;
    private String phone;
    private String passwordHash;
    private Boolean isActive;
    private String other;
}