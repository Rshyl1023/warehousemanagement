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
@TableName("resource")
public class Resource {
    @TableId(value = "code", type = IdType.INPUT)
    private String code;         // 数据库字段: code
    private String name;         // 数据库字段: name
    private String type;         // 数据库字段: type
}