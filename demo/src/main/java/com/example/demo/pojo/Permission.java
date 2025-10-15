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
    @TableId(type = IdType.ASSIGN_ID)
    private String personCode;       // 原 P_CODE（P = Person）
    private String resourceCode;     // 原 R_CODE（R = Resource）
    private Boolean hasPermission;   // 原 HAS_PERMISSION
}