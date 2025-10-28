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
@TableName("material")
public class Material {
    @TableId(value = "code", type = IdType.INPUT)
    private String code;             // 数据库字段: code
    private String name;             // 数据库字段: name
    private String spec;             // 数据库字段: spec
    private String unit;             // 数据库字段: unit
    private Integer stockQty;        // 数据库字段: stock_qty
    private Integer minStock;        // 数据库字段: min_stock
    private String other;            // 数据库字段: other
}