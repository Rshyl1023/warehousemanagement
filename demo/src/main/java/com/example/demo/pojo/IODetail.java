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
@TableName("io_detail")
public class IODetail {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;              // 数据库字段: id
    private String ioNo;             // 数据库字段: io_no
    private String materialCode;     // 数据库字段: material_code
    private String type;             // 数据库字段: type (IN/OUT)
    private Integer qty;             // 数据库字段: qty
}