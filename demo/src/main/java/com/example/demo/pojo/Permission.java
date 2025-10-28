package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("permission")
public class Permission {
    private String personCode;       // 数据库字段: person_code
    private String resourceCode;     // 数据库字段: resource_code
    private Boolean hasPermission;   // 数据库字段: has_permission
}