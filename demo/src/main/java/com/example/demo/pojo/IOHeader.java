package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("io_header")
public class IOHeader {
    @TableId(value = "no", type = IdType.INPUT)
    private String no;               // 数据库字段: no
    private LocalDate date;          // 数据库字段: date
    // 注意：type字段已移除，现在type在io_detail表中
    private String operatorCode;     // 数据库字段: operator_code
    private String handlerCode;      // 数据库字段: handler_code
    private String remark;           // 数据库字段: remark
}