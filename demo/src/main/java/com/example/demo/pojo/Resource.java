package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    @TableId(type = IdType.ASSIGN_ID)
    private String resourceCode;     // 原 R_CODE
    private String resourceName;     // 原 R_NAME
    private String resourceType;     // 原 R_TYPE
}