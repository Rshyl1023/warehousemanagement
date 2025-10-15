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
    @TableId(type = IdType.ASSIGN_ID)
    private String code;     // 原 R_CODE
    private String name;     // 原 R_NAME
    private String type;     // 原 R_TYPE
}